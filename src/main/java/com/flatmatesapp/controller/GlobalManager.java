package com.flatmatesapp.controller;

import java.io.Serializable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("singleton")
@Slf4j
public class GlobalManager implements Serializable {
    private static final long serialVersionUID = 1010234324505869476L;
}
