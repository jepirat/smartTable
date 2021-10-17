/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.smart.table.project;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import com.intelligt.modbus.jlibmodbus.serial.SerialPort;
import com.intelligt.modbus.jlibmodbus.serial.SerialPortException;
import com.intelligt.modbus.jlibmodbus.serial.SerialPortFactoryJSSC;
import com.intelligt.modbus.jlibmodbus.serial.SerialUtils;
import java.io.IOException;
import jssc.SerialPortList;

/**
 *
 * @author jeka
 */
public class Controller {
    private boolean turbine, lamp;
    SerialParameters serialParameters = new SerialParameters();
    Properties properties;
    ModbusMaster modbusMaster;

    public Controller() throws SerialPortException, IOException, ClassNotFoundException {
        properties = new Properties();
        properties.readProperties();
        serialParameters.setDevice(properties.getProperty("port"));
        serialParameters.setBaudRate(SerialPort.BaudRate.BAUD_RATE_9600);
        serialParameters.setDataBits(8);
        serialParameters.setParity(SerialPort.Parity.NONE);
        serialParameters.setStopBits(1);
        SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
        modbusMaster = ModbusMasterFactory.createModbusMasterRTU(serialParameters);   
    }
    
    
    public String[] getComPorts() { 
        return SerialPortList.getPortNames();  
    }  
    
    public boolean turbineOnOf() throws ModbusIOException, ModbusProtocolException, ModbusNumberException {
        modbusMaster.connect();
        if (turbine == false) {
            turbine = true;
            modbusMaster.writeSingleCoil(2, 0, true);              
        } else if (turbine == true) {
           turbine = false;
            modbusMaster.writeSingleCoil(2, 0, false); 
        }
        modbusMaster.disconnect();
        return turbine;
    }
    
    public boolean lampeOnOf() throws ModbusIOException, ModbusProtocolException, ModbusNumberException {
        modbusMaster.connect();
        if (lamp == false) {
            lamp = true;
            modbusMaster.writeSingleCoil(2, 1, true);              
        } else if (lamp == true) {
           lamp = false;
           modbusMaster.writeSingleCoil(2, 1, false); 
        }
        modbusMaster.disconnect();
        return lamp;
    }
    
    public void refreshPorerties() throws IOException, ClassNotFoundException, SerialPortException {
        properties.readProperties();
        serialParameters.setDevice(properties.getProperty("port"));
        serialParameters.setBaudRate(SerialPort.BaudRate.BAUD_RATE_9600);
        serialParameters.setDataBits(8);
        serialParameters.setParity(SerialPort.Parity.NONE);
        serialParameters.setStopBits(1);
        SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
        modbusMaster = ModbusMasterFactory.createModbusMasterRTU(serialParameters);
    }
}
