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
	FC_DO_START_TELEM,
	FC_DO_RESET_INS,
	FC_DO_STOP_TELEM,
	FC_DO_ACC_CALIB,
	FC_DO_GYRO_CALIB,
	FC_DO_MAG_CALIB,
	FC_DO_END_COM,
	FC_DO_SAVE_EEPROM,
	FC_GET_ACC_OFFSET,
	FC_GET_GYRO_OFFSET,
	FC_GET_MAG_OFFSET,
	FC_GET_ACC_MUL,
	FC_GET_GYRO_MUL,
	FC_GET_MAG_MUL,
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
	FC_GET_INS_ACC_INF,
	FC_GET_INS_ACC_LPF,
	FC_GET_INS_GYRO_LPF,
	FC_GET_OVERWRITE_MOTORS,
	FC_GET_RATE_PID_RP,
	FC_GET_RATE_PID_RI,
	FC_GET_RATE_PID_RD,
	FC_GET_RATE_PID_PP,
	FC_GET_RATE_PID_PI,
	FC_GET_RATE_PID_PD,
	FC_GET_RATE_PID_YP,
	FC_GET_RATE_PID_YI,
	FC_GET_RATE_PID_YD,
	FC_GET_M1_OVERWRITE,
	FC_GET_M2_OVERWRITE,
	FC_GET_M3_OVERWRITE,
	FC_GET_M4_OVERWRITE,
	FC_GET_PROPS_IN,
	FC_POST_SENSOR,
	FC_SET_INS_ACC_INF,
	FC_SET_INS_ACC_LPF,
	FC_SET_INS_GYRO_LPF,
	FC_SET_ACC_OFFSET,
	FC_SET_GYRO_OFFSET,
	FC_SET_MAG_OFFSET,
	FC_SET_ACC_MUL,
	FC_SET_GYRO_MUL,
	FC_SET_MAG_MUL,
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
	FC_SET_OVERWRITE_MOTORS,
	FC_SET_M1_OVERWRITE,
	FC_SET_M2_OVERWRITE,
	FC_SET_M3_OVERWRITE,
	FC_SET_M4_OVERWRITE,
	FC_SET_PROPS_IN,
	FC_SET_RATE_PID_RP,
	FC_SET_RATE_PID_RI,
	FC_SET_RATE_PID_RD,
	FC_SET_RATE_PID_PP,
	FC_SET_RATE_PID_PI,
	FC_SET_RATE_PID_PD,
	FC_SET_RATE_PID_YP,
	FC_SET_RATE_PID_YI,
	FC_SET_RATE_PID_YD,
}