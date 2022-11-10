package org.satel.ressatel.bean.card.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Types {
    pdf("application/pdf"),
    doc("application/msword"),
    docx("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    png("image/png"),
    jpeg("image/jpeg"),
    jpg("image/jpeg"),
    gif("image/gif"),
    xls("application/vnd.ms-excel"),
    xlsx("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    ;

    private final String mimeType;

}
