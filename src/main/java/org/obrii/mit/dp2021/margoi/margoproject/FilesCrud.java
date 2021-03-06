package org.obrii.mit.dp2021.margoi.margoproject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author 38068
 */
public class FilesCrud implements DataCrudInterface {

    private File file;

    public FilesCrud() {
    }

    public FilesCrud(File file) {
        this.file = file;
    }

    @Override
    public void writeData(List<Data> data) {

        try ( FileOutputStream f = new FileOutputStream(file);  ObjectOutputStream o = new ObjectOutputStream(f)) {

            data.forEach(d -> {
                try {
                    o.writeObject(d);
                } catch (IOException ex) {
                    Logger.getLogger(FilesCrud.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FilesCrud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FilesCrud.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

      @Override
    public List<Data> searchData(String s) {

 
       
            
            List<Data> newData = new ArrayList<>();
        for (Data d : this.readData()) {
            
            if(d.getName().contains(s)){
                newData.add(d);}
                
            
        }
        

            return newData;

        

    }
    
    
    @Override
    public List<Data> readData() {

        try ( FileInputStream f = new FileInputStream(file);  ObjectInputStream o = new ObjectInputStream(f)) {
            List<Data> result = new ArrayList<>();
            while (f.available() > 0) {
                result.add((Data) o.readObject());
            }

            return result;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FilesCrud.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<Data>();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FilesCrud.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<Data>();
        }

    }

    @Override
    public void createData(Data addingData) {
        List<Data> data = this.readData();
        addingData.setId(data.size());
        data.add(addingData);
        this.writeData(data);

    }

    @Override
    public void deleteData(int id) {        
        List<Data> newData = new ArrayList<>();
        for (Data d : this.readData()) {
            if (d.getId() != id) {
                newData.add(d);
            }
        }
        this.writeData(newData);
    }
    
    @Override
    public void updateData(int id, Data data) {        
        List<Data> newData = new ArrayList<>();
        data.setId(id);
        for (Data d : this.readData()) {
            if (d.getId() != id) {
                newData.add(d);
            } else{ newData.add(data); }
        }
        this.writeData(newData);
    }

       public void stData() {        
        List<Data> newData = new ArrayList<>();
        int i = 0;
        for (Data d : this.readData()) {
            d.setId(i);
            i++;
                newData.add(d);
            
        }
        this.writeData(newData);
    }

    
    public File getFile() {
        return file;
    }

    public void setFileName(File file) {
        this.file = file;
    }

}
