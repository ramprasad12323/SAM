package com.RamPratap.SAM_Lib;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.ftdi.j2xx.D2xxManager;
import com.ftdi.j2xx.FT_Device;

import java.util.Arrays;

public class uC_Device {

    public static final int Input = 1;
    public static final int Output = 2;

    public static final int LOW = 1;
    public static final int HIGH = 2;



    // Variables to Configure the Pins
    public int CP0;
    public int CP1;
    public int CP2;
    public int CP3;
    public int CP4;
    public int CP5;
    public int CP6;
    public int CP7;
    public int CP8;
    public int CP9;
    public int CP10;
    public int CP11;
    public int CP12;
    public int CP13;
    private byte[] pin_state;

    // Variables to Send Data to Pins
    public int SP0;
    public int SP1;
    public int SP2;
    public int SP3;
    public int SP4;
    public int SP5;
    public int SP6;
    public int SP7;
    public int SP8;
    public int SP9;
    public int SP10;
    public int SP11;
    public int SP12;
    public int SP13;
    private byte[] pin_data_to_send;

    // Variables to Receive Data from Pins
    public int RP0;
    public int RP1;
    public int RP2;
    public int RP3;
    public int RP4;
    public int RP5;
    public int RP6;
    public int RP7;
    public int RP8;
    public int RP9;
    public int RP10;
    public int RP11;
    public int RP12;
    public int RP13;
    private byte[] pin_data_to_receive;

    //Request From The Device
    private final byte RequestingConfig = 3;
    private final byte RequestingData = 4;
    private byte Requesting;

    //Request The Device
    private final byte[] RequestData = {5};
    private final byte[] Reset = {6};

    //Device Connection Objects
    private FT_Device device;
    private D2xxManager manager;
    private int deviceCount;

    //Miscellaneous Variables
    private Context context;
    private Activity activity;

    private byte[] data;
    private int byteNum;


    public uC_Device(Context contExt, Activity actIvity){

        context = contExt;
        activity = actIvity;

        // Variables to Configure the Device
        {
            CP0 = 0;
            CP1 = 0;
            CP2 = 0;
            CP3 = 0;
            CP4 = 0;
            CP5 = 0;
            CP6 = 0;
            CP7 = 0;
            CP8 = 0;
            CP9 = 0;
            CP10 = 0;
            CP11 = 0;
            CP12 = 0;
            CP13 = 0;
            pin_state = new byte[14];
        }

        // Variables to Send Data to Pins
        {
            SP0 = 0;
            SP1 = 0;
            SP2 = 0;
            SP3 = 0;
            SP4 = 0;
            SP5 = 0;
            SP6 = 0;
            SP7 = 0;
            SP8 = 0;
            SP9 = 0;
            SP10 = 0;
            SP11 = 0;
            SP12 = 0;
            SP13 = 0;
            pin_data_to_send = new byte[14];

        }

        // Variables to Receive Data from Pins
        {
            RP0 = 0;
            RP1 = 0;
            RP2 = 0;
            RP3 = 0;
            RP4 = 0;
            RP5 = 0;
            RP6 = 0;
            RP7 = 0;
            RP8 = 0;
            RP9 = 0;
            RP10 = 0;
            RP11 = 0;
            RP12 = 0;
            RP13 = 0;
            pin_data_to_receive = new byte[14];
        }

        //Device Connection Objects
        {
            manager = null;
            device = null;
            deviceCount = 0;
        }

        //Miscellaneous Variables
        {
            byteNum = 0;
            data = new byte[20];
        }

    }

    private void Set_Config(){
        pin_state[0]=(byte)CP0;
        pin_state[1]=(byte)CP1;
        pin_state[2]=(byte)CP2;
        pin_state[3]=(byte)CP3;
        pin_state[4]=(byte)CP4;
        pin_state[5]=(byte)CP5;
        pin_state[6]=(byte)CP6;
        pin_state[7]=(byte)CP7;
        pin_state[8]=(byte)CP8;
        pin_state[9]=(byte)CP9;
        pin_state[10]=(byte)CP10;
        pin_state[11]=(byte)CP11;
        pin_state[12]=(byte)CP12;
        pin_state[13]=(byte)CP13;
    }

    private void Set_Data(){
        pin_data_to_send[0]=(byte)SP0;
        pin_data_to_send[1]=(byte)SP1;
        pin_data_to_send[2]=(byte)SP2;
        pin_data_to_send[3]=(byte)SP3;
        pin_data_to_send[4]=(byte)SP4;
        pin_data_to_send[5]=(byte)SP5;
        pin_data_to_send[6]=(byte)SP6;
        pin_data_to_send[7]=(byte)SP7;
        pin_data_to_send[8]=(byte)SP8;
        pin_data_to_send[9]=(byte)SP9;
        pin_data_to_send[10]=(byte)SP10;
        pin_data_to_send[11]=(byte)SP11;
        pin_data_to_send[12]=(byte)SP12;
        pin_data_to_send[13]=(byte)SP13;
    }

    private void Receive_Data(){
        RP0=(int)pin_data_to_receive[0];
        RP1=(int)pin_data_to_receive[1];
        RP2=(int)pin_data_to_receive[2];
        RP3=(int)pin_data_to_receive[3];
        RP4=(int)pin_data_to_receive[4];
        RP5=(int)pin_data_to_receive[5];
        RP6=(int)pin_data_to_receive[6];
        RP7=(int)pin_data_to_receive[7];
        RP8=(int)pin_data_to_receive[8];
        RP9=(int)pin_data_to_receive[9];
        RP10=(int)pin_data_to_receive[10];
        RP11=(int)pin_data_to_receive[11];
        RP12=(int)pin_data_to_receive[12];
        RP13=(int)pin_data_to_receive[13];
    }

    public boolean Initialize(){
        boolean status;

        try {
            manager = D2xxManager.getInstance(context);
            status=true;
        } catch (D2xxManager.D2xxException e) {
            Log.e("UART","Failed To Get Instance");
            status=false;
        }
        return status;
    }

    public int Connect() {
        int status;

        if(device==null){
            deviceCount = manager.createDeviceInfoList(context);
            if (deviceCount > 0) {
                device = manager.openByIndex(context, 0);
                if (device.isOpen()) {
                    // Reset FT Device
                    device.setBitMode((byte) 0, D2xxManager.FT_BITMODE_RESET);
                    // Set Baud Rate
                    device.setBaudRate(115200);
                    // Set Data Bit , Stop Bit , Parity Bit
                    device.setDataCharacteristics(D2xxManager.FT_DATA_BITS_8, D2xxManager.FT_STOP_BITS_1, D2xxManager.FT_PARITY_EVEN);
                    // Set Flow Control
                    device.setFlowControl(D2xxManager.FT_FLOW_NONE, (byte) 0x0b, (byte) 0x0d);
                    status = 1;
                } else {
                    device.close();
                    status = -1;
                }
            } else {
                status = -2;
            }
        }else if (!device.isOpen()){
            deviceCount = manager.createDeviceInfoList(context);
            if (deviceCount > 0) {
                device = manager.openByIndex(context, 0);
                if (device.isOpen()) {
                    // Reset FT Device
                    device.setBitMode((byte) 0, D2xxManager.FT_BITMODE_RESET);
                    // Set Baud Rate
                    device.setBaudRate(115200);
                    // Set Data Bit , Stop Bit , Parity Bit
                    device.setDataCharacteristics(D2xxManager.FT_DATA_BITS_8, D2xxManager.FT_STOP_BITS_1, D2xxManager.FT_PARITY_EVEN);
                    // Set Flow Control
                    device.setFlowControl(D2xxManager.FT_FLOW_NONE, (byte) 0x0b, (byte) 0x0d);
                    status = 1;
                } else {
                    device.close();
                    status = -1;
                }
            } else {
                status = -2;
            }
        }else{
            status = 2;
        }
        return status;
    }

    public int Disconnect(){
        int status=0;
        if(device!=null&&device.isOpen()) {
            device.close();
            status=1;
        }else{
            status=-1;
        }
        return status;
    }

    private byte Check_Request(Activity activity){
        byte toReturn=0;
        byteNum=device.getQueueStatus();
        if(byteNum>0) {
            device.read(data, 1);
            toReturn = data[0];
        }else if(byteNum==0){
            toReturn=-3;
        }
        byteNum=0;
        return toReturn;
    }

    public int Configure(){
        int inc=0;
        if(device!=null&&device.isOpen()) {

            byteNum=device.getQueueStatus();
            if(byteNum>0) {
                device.read(data, byteNum);
                byteNum=0;
                Arrays.fill(data, (byte)0);
            }

            device.write(Reset, 1);

            Requesting = Check_Request(activity);

            while(Requesting!=RequestingConfig){
                if(Requesting != RequestingConfig&&inc>100000){
                    break;
                }
                inc=inc+1;
                Requesting = Check_Request(activity);
            }
            if(Requesting==RequestingConfig) {
                Set_Config();
                device.write(pin_state, pin_state.length);
            }else{
                Requesting=-1;
            }
        }
        else{
            Requesting=-2;
        }
        return Requesting;
    }

    public int Send_Data(){
        int inc=0;
        if(device!=null&&device.isOpen()) {

            Requesting = Check_Request(activity);

            while(Requesting!=RequestingData){
                if(Requesting != RequestingData&&inc>100000){
                    break;
                }
                inc=inc+1;
                Requesting = Check_Request(activity);
            }
            if(Requesting==RequestingData) {
                Set_Data();
                device.write(pin_data_to_send, pin_data_to_send.length);
            }else{
                Requesting=-1;
            }
        }else{
            Requesting=-2;
        }
        return Requesting;
    }

    public int Read_Data(){
        int inc=0;
        int i = 0;
        if(device!=null&&device.isOpen()) {
            Requesting = Check_Request(activity);

            while (Requesting != RequestingData) {
                if (Requesting != RequestingData && i > 100000) {
                    break;
                }
                i = i + 1;
                Requesting = Check_Request(activity);
            }

            if (Requesting == RequestingData) {
                device.write(RequestData, 1);
                while (inc < 14) {
                    Requesting = Check_Request(activity);
                    while (Requesting == -3) {
                        int control = 0;
                        Requesting = Check_Request(activity);
                        control = control + Requesting;
                        if (control < -500 || Requesting != -3) {
                            break;
                        }
                    }
                    pin_data_to_receive[inc] = Requesting;
                    inc = inc + 1;
                }
                Requesting = 1;
            } else {
                Requesting = -1;
            }
        }else{
        Requesting=-2;
    }
        Receive_Data();
        return Requesting;
    }

    public void Reset_Device(){
        device.write(Reset,1);
    }



}
