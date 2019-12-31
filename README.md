# SAM [Smart Android Microcontroller]
Arduino Side 
Upload the Arduino firmware .hex files in the Arduino nano board using Xloader.This will work only for Arduino nano boards having FTDI serial chip on it.
	
Android Side 
Copy the .aar file in the lib folder. Left click on it and Add library or file -> project structure -> Dependencies click the plus sign on dependencies and click on JAR dependencies in step 1 drop down the list and select libs\SAM_Lib_release.aar and click ok then click on apply and ok. The library is sucessfully added.

First import the Package

	import com.RamPratap.SAM_Lib.uC_Device;

Create the Object in main activity

	 uC_Device con;
Initilize the device in onCreate method

	con = new uC_Device(this,this);
	con.Initialize();
Returns true if sucessful and false if not sucessful that means your mobile phone does not support the OTG. Then connect to the device

	int a= con.Connect();
Returns -2 if the device is not connected, -1 if permission not given, 1 if connected and 2 if already Conect() function is called.
Configure the pins as input or output as following. The pins are from CP0 to CP27 where CP0 to CP13 are Digital I/O pins, CP14 to CP19 are 8-bit PWM (pin 3,5,6,9,10,11 respectively) outputs which can be only set as output or not output and CP20 to CP27 are 10-bit Analog (A0,A1,A2,A3,A4,A5,A6,A7 respectively) inputs which can be only set as input or not input.

	con.CP13= uC_Device.Output;
	con.CP12= uC_Device.Input;
	con.CP17= uC_Device.Output;
	con.CP25= uC_Device.Input;
Configure the Device as follows

	int i = con.Configure();
Returns -2 if the device is not connected, -1 if not transmitted sucessfully it is the problem from hardware side and 3 if configured sucessfully. To send pin values set the pins which are from SP0 to SP19 where SP0 to SP13 are Digital Outputs which can be set as High or Low and SP14 to SP19 are 8-bit PWM (pin 3,5,6,9,10,11 respectively) Outputs which can be set a value between 0-255.

	con.SP13 = uC_Device.HIGH;
	con.SP12 = uC_Device.LOW; // this will not be set the pin, because it is configured as Input
	con.SP17 = 185;
If a pin is configured as input and if you are trying to send value to the pin, it will not set the pin. Send the data as follows
	
	int in = con.Send_Data();
Returns -2 values if the device is not connected, -1 if not transmitted sucessfully it is the problem from hardware side and 4 if transmitted sucessfully. To read the pin values

	int j=con.Read_Data();
Returns -2 if the device is not connected, -1 if the receive is not sucessfull, 1 if the recive is sucessfull. After calling the Read_Data() function the data is read in the variables from con.RP0 to con.RP20 where con.RP0 to con.RP13 are Digital Input and con.RP14 to con.RP20 (A0,A1,A2,A3,A4,A5,A6,A7 respectively) are Analog Inputs.

	int a=con.RP12;
	int b=con.RP25
Here 0 means no data, 1 means low and 2 means high and the Analog pins have the 10-bit value between 0-1023. For reseting the Device.

	con.Reset_Device();
