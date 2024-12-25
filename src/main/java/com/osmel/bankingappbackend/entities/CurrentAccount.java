package com.osmel.bankingappbackend.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CA")
@Data @NoArgsConstructor
@AllArgsConstructor
public class CurrentAccount extends Account{
    private Double overDraft;
}
