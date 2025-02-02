package com.gk.campaign.payload.request;


import com.gk.campaign.utils.enums.CMRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRolesRequest {

    @NotBlank
    private String userName;

    @NotEmpty
    private Set<CMRole> roles;
}
