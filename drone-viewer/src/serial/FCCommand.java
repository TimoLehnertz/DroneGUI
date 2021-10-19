package serial;

/**
 * enum for all commands wich can be understood by the flight controller
 * 
 * Comunication protocol:
 * 	types of messages:
 * 		FC_DO   - commands like do calibration no response
 * 		FC_GET	- get commands wich the fc has to answer with a post
 * 		FC_RES	- Response on FC_GET
 * 		FC_POST	- 
 * 
 * 	FC_GET in the form of FC_GET_<Command> <id>
 * 		<id> represents an id wich is unique to each get request to be echoed back in the post response can be any string
 * 
 * 	FC_RES in the form of FC_RES <id> <response>
 * 		<id> the requests id
 * 		<response> response string
 * 	
 * 	FC_POST in the form of FC_POST_<command> <body>
 * 		<command> the name of the post command
 * 		<body> the string to be posted
 * 
 * FC_SET in the form of FC_SET_<command> <id> <body>
 * 		<command> the name of the SET command
 * 		<id> the set id. this id will get echoed back to verify succsess
 * 		<body> the string to be Setted
 * 		The FC has to reply with a response to the id echoing the original body to know that everything has gone right
 * 
 * 	
 * 	All messages can have MAXIMUM of 254 bytes in length
 * 
 * @author Timo Lehnertz
 *
 */
public enum FCCommand {
	/**
	 * FC_DO
	 */
	FC_DO_START_TELEM,
	FC_DO_RESET_INS,
	FC_DO_STOP_TELEM,
	/**
	 * Calibration
	 */
	FC_DO_ACC_CALIB,
	FC_DO_ACC_CALIB_QUICK,
	FC_DO_GYRO_CALIB,
	FC_DO_MAG_CALIB,
//	FC_DO_STOP_MAG_CALIB,
	FC_DO_END_COM,
	FC_DO_ERASE_EEPROM,
	FC_DO_SAVE_EEPROM,
	FC_DO_REBOOT,
	FC_DO_SET_ACC_ANGLE_OFFSET,

	/**
	 * FC_GET
	 */
	
	/**
	 * Calibration
	 */
	// Acc
	FC_GET_ACC_OFFSET,
	FC_GET_ACC_SCALE,
	// Gyro
	FC_GET_GYRO_OFFSET,
	FC_GET_GYRO_MUL,
	//Mag
	FC_GET_MAG_HARD_IRON,
	FC_GET_MAG_SOFT_IRON,
	FC_GET_MAG_OFFSET,
	FC_GET_MAG_SCALE,

	FC_GET_ACC_LPF,
	FC_GET_GYRO_LPF,
	FC_GET_USE_ACC_TELEM,
	FC_GET_USE_GYRO_TELEM,
	FC_GET_USE_MAG_TELEM,
	FC_GET_USE_BARO_TELEM,
	FC_GET_USE_GPS_TELEM,
	FC_GET_USE_QUAT_TELEM,
	FC_GET_USE_ATTI_TELEM,
	FC_GET_USE_VEL_TELEM,
	FC_GET_USE_LOC_TELEM,
	FC_GET_USE_BAT_TELEM,
	FC_GET_COMPLEMENTARY_ACC_INF,
	FC_GET_INS_ACC_LPF,
	FC_GET_INS_GYRO_LPF,
	FC_GET_OVERWRITE_MOTORS,
	FC_GET_I_RELAX_MIN_RATE,
	FC_GET_INS_ACC_MAX_G,
	
	FC_GET_RATE_PID_R,
	FC_GET_RATE_PID_P,
	FC_GET_RATE_PID_Y,
	
	FC_GET_LEVEL_PID_R,
	FC_GET_LEVEL_PID_P,
	FC_GET_LEVEL_PID_Y,
	
	FC_GET_M1_OVERWRITE,
	FC_GET_M2_OVERWRITE,
	FC_GET_M3_OVERWRITE,
	FC_GET_M4_OVERWRITE,
	FC_GET_PROPS_IN,
	FC_GET_USE_TIMING,
	FC_GET_USE_RC_TELEM,
	FC_GET_USE_FC_TELEM,
	FC_GET_LOOP_FREQ_RATE,
	FC_GET_LOOP_FREQ_LEVEL,
	FC_GET_COMPLEMENTARY_MAG_INF,
	FC_GET_USE_LEDS,
	
	FC_GET_USE_ANTI_GRAVITY,
	FC_GET_ANTI_GRAVITY_MULTIPLICATOR,
	FC_GET_ANTI_GRAVITY_SPEED,
	FC_GET_ANTI_GRAVITY_LPF,
	FC_GET_OVERWRITE_FM,
	
	FC_GET_BAT_LPF,
	
	FC_GET_USE_VCELL,
	
	FC_GET_ACC_ANGLE_OFFSET,
	
	FC_GET_SENSOR_FUSION,
	FC_GET_0,
	
	
	FC_POST_SENSOR,
	
	/**
	 * FC_SET
	 */
	/**
	 * Calibration
	 */
	// Acc
	FC_SET_ACC_OFFSET,
	FC_SET_ACC_SCALE,
	// Gyro
	FC_SET_GYRO_OFFSET,
	FC_SET_GYRO_MUL,
	//Mag
	FC_SET_MAG_HARD_IRON,
	FC_SET_MAG_SOFT_IRON,
	FC_SET_MAG_OFFSET,
	FC_SET_MAG_SCALE,
	
	FC_SET_COMPLEMENTARY_ACC_INF,
	FC_SET_COMPLEMENTARY_MAG_INF,
	FC_SET_INS_ACC_LPF,
	FC_SET_INS_GYRO_LPF,
	FC_SET_ACC_MUL,
    FC_SET_ACC_LPF,
	FC_SET_GYRO_LPF,
	FC_SET_ACC_TELEM,
	FC_SET_GYRO_TELEM,
	FC_SET_MAG_TELEM,
	FC_SET_BARO_TELEM,
	FC_SET_GPS_TELEM,
	FC_SET_QUAT_TELEM,
	FC_SET_ATTI_TELEM,
	FC_SET_VEL_TELEM,
	FC_SET_LOC_TELEM,
	FC_SET_BAT_TELEM,
	FC_SET_OVERWRITE_MOTORS,
	FC_SET_M1_OVERWRITE,
	FC_SET_M2_OVERWRITE,
	FC_SET_M3_OVERWRITE,
	FC_SET_M4_OVERWRITE,
	FC_SET_PROPS_IN,
	
	FC_SET_RATE_PID_R,
	FC_SET_RATE_PID_P,
	FC_SET_RATE_PID_Y,
	
	FC_SET_LEVEL_PID_R,
	FC_SET_LEVEL_PID_P,
	FC_SET_LEVEL_PID_Y,
	
	FC_SET_USE_TIMING,
	FC_SET_USE_RC_TELEM,
	FC_SET_USE_FC_TELEM,
	FC_SET_LOOP_FREQ_RATE,
	FC_SET_LOOP_FREQ_LEVEL,
	FC_SET_USE_LEDS,
	FC_SET_I_RELAX_MIN_RATE,
	
	FC_SET_USE_ANTI_GRAVITY,
	FC_SET_ANTI_GRAVITY_MULTIPLICATOR,
	FC_SET_ANTI_GRAVITY_SPEED,
	FC_SET_ANTI_GRAVITY_LPF,
	
	FC_SET_OVERWRITE_FM,
	
	FC_SET_INS_ACC_MAX_G,
	
	FC_SET_VOLTAGE_CALIB,
	
	FC_SET_BAT_LPF,
	
	FC_SET_USE_VCELL,
	
	FC_SET_ACC_ANGLE_OFFSET,
	
	FC_SET_SENSOR_FUSION,
	FC_SET_ACC_CALIB_SIDE,
}