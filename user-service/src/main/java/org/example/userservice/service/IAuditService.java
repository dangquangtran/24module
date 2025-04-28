package org.example.userservice.service;

import org.example.userservice.entity.User;

import java.util.List;

public interface IAuditService {
    List<User> getRevisions(Long id);
}
