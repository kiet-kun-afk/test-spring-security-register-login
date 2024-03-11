package com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer subId;

    private String name;

    public enum RoleEnum {

        MANAGER(1),
        STAFF(2);

        private final int value;

        RoleEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public Role(RoleEnum role) {
        this.subId = role.getValue();
        this.name = role.name();
    }
}
