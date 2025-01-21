package org.example.databasehw.controller;

import org.example.databasehw.model.Avatar;
import org.example.databasehw.service.AvatarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/avatar")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<Avatar> uploadAvatar(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            Avatar avatar = avatarService.uploadAvatar(id, file);
            return ResponseEntity.ok(avatar);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avatar> getAvatarByStudentId(@PathVariable Long id) {
        try {
            Avatar avatar = avatarService.getAvatarByStudentId(id);
            return ResponseEntity.ok(avatar);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<Page<Avatar>> getAllAvatars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Avatar> avatars = avatarService.getAllAvatars(PageRequest.of(page, size));
        return ResponseEntity.ok(avatars);
    }
}
