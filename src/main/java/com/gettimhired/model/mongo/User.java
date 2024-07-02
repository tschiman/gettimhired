package com.gettimhired.model.mongo;

import org.springframework.data.annotation.Id;

public record User(@Id String id, String password) {
}
