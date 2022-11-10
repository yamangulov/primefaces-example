package org.satel.ressatel.bean.card.file;

import jakarta.enterprise.context.RequestScoped;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FilesUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import org.primefaces.util.EscapeUtils;
import org.satel.ressatel.entity.File;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@Component("fileUploadView")
@RequestScoped
@Getter
@Setter
@Log4j2
public class FileUploadView implements UploadView {

    private UploadedFile uploadedFile;
    private UploadedFiles uploadedFiles;

    private List<File> files = new ArrayList<>();
    private Integer id;
    private Integer fileType;

    public void upload() {
        if (uploadedFile != null) {
            FacesMessage message = new FacesMessage("Successful", uploadedFile.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void uploadMultiple() {
        if (uploadedFiles != null) {
            for (UploadedFile f : uploadedFiles.getFiles()) {
                FacesMessage message = new FacesMessage("Successful", f.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        File file = new File();
        file.setName(event.getFile().getFileName());
        file.setContent(event.getFile().getContent());
        file.setType(1);
        files.add(file);
        FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void handleFileUploadTextarea(FileUploadEvent event) {
        String jsVal = "PF('textarea').jq.val";
        String fileName = EscapeUtils.forJavaScript(event.getFile().getFileName());
        PrimeFaces.current().executeScript(jsVal + "(" + jsVal + "() + '\\n\\n" + fileName + " uploaded.')");
    }

    public void handleFilesUpload(FilesUploadEvent event) {
        for (UploadedFile f : event.getFiles().getFiles()) {
            File file = new File();
            file.setName(f.getFileName());
            file.setContent(f.getContent());
            file.setType(1);
            files.add(file);
            FacesMessage message = new FacesMessage("Successful", f.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

}
