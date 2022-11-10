package org.satel.ressatel.bean.card.file;

import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

public interface UploadView {
    UploadedFiles getUploadedFiles();

    UploadedFile getUploadedFile();
}
