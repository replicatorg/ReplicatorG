(**** start.gcode for The Replicator, Dualstrusion! ****)
M103 (disable RPM)
M73 P0 (enable build progress)
G21 (set units to mm)
G90 (set positioning to absolute)
G10 P1 X0 Y0 Z0 (Designate T0 Offset)
G10 P2 X33 Y0 Z0 (Designate T1 Offset)
G54 (Recall offset cooridinate system for T0)
M109 S110 T0 (set HBP temperature)
M104 S220 T0 (set extruder temperature)
M104 S220 T1 (set extruder temperature)
(**** begin homing ****)
G162 X Y F2500 (home XY axes maximum)
G161 Z F1100 (home Z axis minimum)
G92 Z-5 (set Z to -5)
G1 Z0.0 (move Z to "0")
G161 Z F100 (home Z axis minimum)
M132 X Y Z A B (Recall stored home offsets for XYZAB axis)
(**** end homing ****)
G1 X-110.5 Y-74 Z150 F3300.0 (move to waiting position)
G130 X0 Y0 A0 B0 (Lower stepper Vrefs while heating)
M6 T0 (wait for toolhead, and HBP to reach temperature)
M6 T1 (wait for toolhead, and HBP to reach temperature)
G130 X127 Y127 A127 B127 (Set Stepper motor Vref to defaults)
G0 X-110.5 Y-74     (Position Nozzle)
G0 Z0.5          (Position Height)
G1 E4 F50.0      (Create Anchor)
G92 E0
(**** end of start.gcode ****)
