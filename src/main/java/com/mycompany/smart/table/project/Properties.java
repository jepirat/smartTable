/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.smart.table.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jeka
 */
public class Properties {
    Map<String, String> properties;
    public void saveProperty(String key, String value) throws IOException {
        File file = new File("property");
        if(!file.exists()) {
            file.createNewFile();
        }
        
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
           Map<String, String> properties = new HashMap();
           properties.put(key, value);
           oos.writeObject(properties);
        } 
    }
    
    public void readProperties() throws IOException, ClassNotFoundException {
        File file = new File("property");
        if(!file.exists()) {
            file.createNewFile();
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        this.properties = (HashMap) objectInputStream.readObject();
       
    } 
    
    public String getProperty(String key) {
       return this.properties.get(key);
    }
    
    
}
