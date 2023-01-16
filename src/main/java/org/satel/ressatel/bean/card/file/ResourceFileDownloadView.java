package org.satel.ressatel.bean.card.file;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.satel.ressatel.entity.File;
import org.satel.ressatel.service.FileService;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component("resourceFileDownloadView")
@RequestScoped
@Getter
@Setter
public class ResourceFileDownloadView implements Serializable {
    private Set<Map.Entry<Integer, StreamdContentWrapper>> streamedContentEntrySet;
    private final FileService fileService;

    private String id;
    private Integer fileType;

    @Inject
    public ResourceFileDownloadView(FileService fileService) {
        this.fileService = fileService;
    }

    @Getter
    @Setter
    static
    public class StreamdContentWrapper {
        String name;
        StreamedContent streamedContent;

        public StreamdContentWrapper(String name, StreamedContent streamedContent) {
            this.name = name;
            this.streamedContent = streamedContent;
        }
    }

    public void onload() {
        setStreamedContentListByCompanyIdAndFileType(id, fileType);
    }

    public void setStreamedContentListByCompanyIdAndFileType(String id, Integer type) {
        Map<Integer, StreamdContentWrapper> streamedContentMap = new HashMap<>();
        List<File> fileList = fileService.getFilesByResourcesCompanyIdAndType(id, type);
        fileList.forEach(file -> {
            StreamedContent streamedContent = DefaultStreamedContent.builder()
                    .name(file.getName())
                    .contentType(getContentType(file))
                    .stream(() -> new ByteArrayInputStream(file.getContent()))
                    .build();
            StreamdContentWrapper wrapper = new StreamdContentWrapper(file.getName(), streamedContent);
            streamedContentMap.put(file.getId(), wrapper);
        });
        streamedContentEntrySet = streamedContentMap.entrySet();
    }

    private static String getContentType(File file) {
        String[] nameParts = file.getName().split("\\.");
        String fileExtention = nameParts[nameParts.length - 1];
        return Types.valueOf(fileExtention).getMimeType();
    }

}
