package replicatorg.drivers.gen3;

import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;

import org.w3c.dom.Element;

import replicatorg.app.Base;
import replicatorg.drivers.RetryException;
import replicatorg.machine.model.AxisId;
import replicatorg.machine.model.MachineModel;
import replicatorg.machine.model.ToolModel;
import replicatorg.util.Point5d;

public class Makerbot4GDriver extends Sanguino3GDriver {

	public String getDriverName() {
		return "Makerbot4G";
	}

	protected void queueAbsolutePoint(Point5d steps, long micros) throws RetryException {
		PacketBuilder pb = new PacketBuilder(MotherboardCommandCode.QUEUE_POINT_EXT.getCode());

		if (Base.logger.isLoggable(Level.FINE)) {
			Base.logger.log(Level.FINE,"Queued absolute point " + steps + " at "
					+ Long.toString(micros) + " usec.");
		}

		// override steps on axis controllers
		for (Map.Entry<AxisId,ToolModel> entry : stepExtruderMap.entrySet()) {
			ToolModel curTool = machine.currentTool(); 
			if (curTool.equals(entry.getValue()) && curTool.isMotorEnabled()) {
				boolean clockwise = machine.currentTool().getMotorDirection() == ToolModel.MOTOR_CLOCKWISE;
				long toolSteps = micros; // fixme
				steps.setAxis(entry.getKey(), clockwise?toolSteps:-toolSteps);
			} else {
				steps.setAxis(entry.getKey(), 0);
			}
		}
		
		// just add them in now.
		pb.add32((int) steps.x());
		pb.add32((int) steps.y());
		pb.add32((int) steps.z());
		pb.add32((int) steps.a());
		pb.add32((int) steps.b());
		pb.add32((int) micros);

		runCommand(pb.getPacket());
	}

	public void setCurrentPosition(Point5d p) throws RetryException {
//		System.err.println("   SCP: "+p.toString()+ " (current "+getCurrentPosition().toString()+")");
//		if (super.getCurrentPosition().equals(p)) return;
//		System.err.println("COMMIT: "+p.toString()+ " (current "+getCurrentPosition().toString()+")");
		PacketBuilder pb = new PacketBuilder(MotherboardCommandCode.SET_POSITION_EXT.getCode());

		Point5d steps = machine.mmToSteps(p);
		pb.add32((long) steps.x());
		pb.add32((long) steps.y());
		pb.add32((long) steps.z());
		pb.add32((long) steps.a());
		pb.add32((long) steps.b());

		Base.logger.log(Level.FINE,"Set current position to " + p + " (" + steps
					+ ")");

		runCommand(pb.getPacket());

		super.setCurrentPosition(p);
	}
	
	EnumMap<AxisId,ToolModel> stepExtruderMap = new EnumMap<AxisId,ToolModel>(AxisId.class);
	
	@Override
	/**
	 * When the machine is set for this driver, some toolheads may poach the an extrusion axis.
	 */
	public void setMachine(MachineModel m) {
		super.setMachine(m);
		for (ToolModel tm : m.getTools()) {
			Element e = (Element)tm.getXml();
			if (e.hasAttribute("stepaxis")) {
				final String stepAxisStr = e.getAttribute("stepaxis");
				try {
					AxisId axis = AxisId.valueOf(stepAxisStr);
					if (m.hasAxis(axis)) {
						// If we're seizing an axis for an extruder, remove it from the available axes and get
						// the data associated with that axis.
						stepExtruderMap.put(axis,tm);
						m.getAvailableAxes().remove(axis);
					} else {
						Base.logger.severe("Tool claims unavailable axis "+axis.name());
					}
				} catch (IllegalArgumentException iae) {
					Base.logger.severe("Unintelligible axis designator "+stepAxisStr);
				}
			}
		}
	}
	
	protected Point5d reconcilePosition() {
		if (fileCaptureOstream != null) {
			return new Point5d();
		}
		PacketBuilder pb = new PacketBuilder(MotherboardCommandCode.GET_POSITION_EXT.getCode());
		PacketResponse pr = runQuery(pb.getPacket());
		Point5d steps = new Point5d(pr.get32(), pr.get32(), pr.get32(), pr.get32(), pr.get32());
		// Useful quickie debugs
//		System.err.println("Reconciling : "+machine.stepsToMM(steps).toString());
		return machine.stepsToMM(steps);
	}

}
