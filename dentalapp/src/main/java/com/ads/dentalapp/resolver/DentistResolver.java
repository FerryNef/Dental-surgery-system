package com.ads.dentalapp.resolver;

import com.ads.dentalapp.model.Dentist;
import com.ads.dentalapp.service.DentistService;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DentistResolver {

    @Autowired
    private final DentistService dentistService;

    public Dentist getDentist(DataFetchingEnvironment env) {
        Long id = getLongArgument(env, "id");
        return dentistService.getDentistById(id);
    }

    public List<Dentist> getAllDentists(DataFetchingEnvironment env) {
        return dentistService.getAllDentists();
    }

    private Long getLongArgument(DataFetchingEnvironment env, String argName) {
        Object raw = env.getArgument(argName);
        return (raw instanceof Integer) ? ((Integer) raw).longValue() : (Long) raw;
    }
}
