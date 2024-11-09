package com.shinchik.cloudkeeper.storage.dto.file;

import com.shinchik.cloudkeeper.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileCheckDto extends FileRequestDto{
    private String objName;

    public FileCheckDto(User user, String path, String objName) {
        super(user, path);
        this.objName = objName;
    }

}
