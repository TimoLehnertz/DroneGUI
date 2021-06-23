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
 * 	
 * 	All messages can have MAXIMUM of 254 bytes in length
 * 
 * @author Timo Lehnertz
 *
 */
public enum FCCommand {
	
	//commands
	FC_DO_ACC_CALIB,
	FC_DO_GYRO_CALIB,
	FC_DO_MAG_CALIB,

	//getters
	FC_GET_ACC_CALIB,
	FC_GET_GYRO_CALIB,
	
	//setter
	FC_POST_TEST1
}