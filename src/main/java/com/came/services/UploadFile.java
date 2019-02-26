package com.came.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;
import org.primefaces.model.UploadedFile;

public class UploadFile {

    public void crearCarpetasNecesarias(String ubi) {
        File repo = new File(SessionService.rutaDoc + "/RepositorioCAME");
        if (!repo.exists()) {
            repo.mkdir();
        }
        File img = new File(SessionService.rutaDoc + "/RepositorioCAME/img");
        if (!img.exists()) {
            img.mkdir();
        }
        if (ubi != null) {
            File bib = new File(SessionService.rutaDoc + "/RepositorioCAME/" + ubi);
            if (!bib.exists()) {
                bib.mkdir();
            }
        }
    }

    //Solo para manejar Imagenes
    public String savePhoto(UploadedFile file,String nombreImagen) throws Exception {
        crearCarpetasNecesarias(null);
        try {
            if (ImageIO.read(file.getInputstream()) != null) {

                String NuevoNombre = nombreImagen + ".jpg"; //Generamos un nombre generico

                //Metodo para guardar la imagen
                Thumbnails.of(file.getInputstream()).forceSize(800, 800).toFile(new File(SessionService.rutaDoc + "/RepositorioCAME/img/" + NuevoNombre));
                return NuevoNombre;
            } else {
                return null;
            }
        } catch (IOException e) {
            throw e;
        }
    }

    public String saveOrg(UploadedFile file,String nombreOrganigrama) throws Exception {
        crearCarpetasNecesarias(null);
        try {
            if (ImageIO.read(file.getInputstream()) != null) {

                String NuevoNombre = nombreOrganigrama + ".jpg"; //Generamos un nombre generico

                //Metodo para guardar la imagen
                Thumbnails.of(file.getInputstream()).scale(1).toFile(new File(SessionService.rutaDoc + "/RepositorioCAME/org/" + NuevoNombre));
                return NuevoNombre;
            } else {
                return null;
            }
        } catch (IOException e) {
            throw e;
        }
    }
    
    

    //Solo para manejar documentos
    public String saveDocument(UploadedFile file, String ubi) throws Exception {
        crearCarpetasNecesarias(ubi);
        String documentos[] = {"doc", "docx", "pdf", "xlsx", "xls", "pptx", "ppt"};
        try {
            String[] temp = file.getFileName().replace(".", "/").split("/");
            String document = temp[temp.length - 1];

            if (buscar(documentos, document)) {
                String Nombre = file.getFileName().substring(0, file.getFileName().length() - document.length()) + document.toLowerCase();
                File destino = new File(SessionService.rutaDoc + "/RepositorioCAME/" + ubi + "/" + Nombre);
                if (destino.exists()) {
                    return "Existe";
                }
                destino.delete();
                InputStream in = file.getInputstream();
                OutputStream out = new FileOutputStream(destino);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                return Nombre;
            } else {
                return null;
            }
        } catch (IOException e) {
            throw e;
        }
    }
    
    public String saveCV(UploadedFile file, String ubi) throws Exception {
        crearCarpetasNecesarias(null);
        String documentos[] = {"doc", "docx", "pdf", "xlsx", "xls", "pptx", "ppt"};
        try {
            String[] temp = file.getFileName().replace(".", "/").split("/");
            String document = temp[temp.length - 1];

            if (buscar(documentos, document)) {
                String Nombre = file.getFileName().substring(0, file.getFileName().length() - document.length()) + document.toLowerCase();
                File destino = new File(SessionService.rutaDoc + "/RepositorioCAME/cv/" + Nombre);
                if (destino.exists()) {
                    return "Existe";
                }
                destino.delete();
                InputStream in = file.getInputstream();
                OutputStream out = new FileOutputStream(destino);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                return Nombre;
            } else {
                return null;
            }
        } catch (IOException e) {
            throw e;
        }
    }

    //Validar si el domumento es de formato correcto
    public boolean buscar(String[] lista, String ext) {
        for (String string : lista) {
            if (string.equals(ext.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
