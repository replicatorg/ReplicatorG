(** beginning of start.gcode **)
(This is code for a MakerBot Replicator running a single material print)
M73 P0 (enable build progress display)
G21 (set units to millimeters)
G90 (set positioning to absolute)
G10 P1 X16.5 Y0 Z-0.3  (Designate T0 Offset)
G10 P2 X-16.5 Y0 Z-0.3 (Designate T1 Offset)
M104 S225 T1 (set extruder temperature)
M109 S110 T1 (set HBP temperature)
M70 P5 (Waiting on Heaters..)
(***** begin homing *****)
G161 Z F1100 (home Z axis to minimum)
G92 Z-5 (duck Z down 5)
G1 Z0.0 (duck Z down 5)
G162 X Y F2500 (home XY axes maximum)
G161 Z F100 (home Z axis minimum)
M132 X Y (Recall stored home offsets for XY axis)
G92 Z0 A0 B0 (Set Z, A, B to 0) 
(*****  end  homing *****)
(***** Begin anchor *****)
G1 X117 Y-65 Z10 F3300.0 (move to waiting position)
G55 (Recall offset coordinate system for T0)
M6 T1 (wait for toolhead parts, nozzle, HBP, etc., to reach temperature)
M108 R3.0 T1
G0 X117 Y-70 (Position Nozzle)
G0 Z0.6     (Position Height)
M108 R4.0   (Set Extruder Speed)
M101        (Start Extruder)
G4 P1500    (Create Anchor)
(*****  end  anchor *****)
(** end of start.gcode **)