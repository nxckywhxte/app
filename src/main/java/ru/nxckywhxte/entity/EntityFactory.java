package ru.nxckywhxte.entity;

import ru.nxckywhxte.entity.user.UserEntity;

public class EntityFactory {

    /**
     * Создает представление для конкретного @Entity.
     */

    public EntityClass create(String entityType) {
        switch (entityType) {
            case "user" -> {
                return new UserEntity();
            }
            case "admin" -> {
                return null;
            }
            case "student" -> {
                return null;
            }
            case "teacher" -> {
                return null;
            }
            default -> {
                return null;
            }
        }
    }
}
