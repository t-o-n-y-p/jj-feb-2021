package org.levelp.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {
    @Override
    public String convertToDatabaseColumn(UserStatus userStatus) {
        return userStatus.name().toLowerCase();
    }

    @Override
    public UserStatus convertToEntityAttribute(String s) {
        return UserStatus.valueOf(s.toUpperCase());
    }
}
