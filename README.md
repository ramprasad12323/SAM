# SAM [Smart Android Microcontroller]
Arduino Side 
Upload the Arduino firmware .hex files according to the board in the arduino microcontroller using Xloader. The files containing wdt means the board should support wdt reset.
	
Android Side 
Copy the .aar file in the lib folder. Left click on it and Add library or file -> project structure -> Dependencies click the plus sign on dependencies and click on SAM_Lib and click on again apply and ok. The library is sucessfully added.

First import the Package

	import com.RamPratap.SAM_Lib.uC_Device;

Create the Object in main activity

	 uC_Device con;
Initilize the device in onCreate method

	con = new uC_Device(this,this);
	con.Initialize();
Returns true if sucessful and false if not sucessful that means yhe mobile phone does not support the OTG. Then connect to the device

	int a= con.Connect();
Returns -2 if the device is not connected, -1 if permission not given, 1 if connected and 2 if already Conect() function is called
Set the pins as input or output as following the pins are from CP0 to CP13

	con.CP13= uC_Device.Output;
	con.CP12= uC_Device.Input;
	con.CP8= uC_Device.Input;
Configure the Device as follous

	        int i = con.Configure();
Returns -2 values if the device is not connected, -1 if not configured it is the problem from hardware side and 3 if configured. To send pin values

	con.SP13 = uC_Device.HIGH;
	con.SP12 = uC_Device.LOW;
If a pin is configured as input and if you are trying to send bit to the pin it will noe accept and will not give any error message automatically. To read the pin values

	int j=con.Read_Data();
Returns -2 if the device is not connected, -1 if the receive is not sucessfull, 1 if the recive is sucessfull. After calling the Read_Data() function the data can be obtained as follows

	int[] i={con.RP0,con.RP1,con.RP2,con.RP3,con.RP4,con.RP5,con.RP6,con.RP7,con.RP8,con.RP9,con.RP10,con.RP11,con.RP12,con.RP13};
Here 0 means no data, 1 means low and 2 means high. For reseting the Device

	con.Reset_Device();
        

In future other abstrictions like PWM, Analog Input, Serial etc will be added.
