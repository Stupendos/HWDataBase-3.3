package org.example.databasehw.service;

import org.example.databasehw.model.Avatar;
import org.example.databasehw.model.Student;
import org.example.databasehw.repository.AvatarRepository;
import org.example.databasehw.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    public Avatar uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Студент с айди " + studentId + " не найден."));

        Avatar avatar = avatarRepository.findById(studentId)
                .orElse(new Avatar());

        avatar.setStudent(student);
        avatar.setFilePath(file.getOriginalFilename());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        return avatarRepository.save(avatar);
    }

    public Avatar getAvatarByStudentId(Long studentId) {
        return avatarRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Аватар для студента с айди " + studentId + " не найден."));
    }

    public Page<Avatar> getAllAvatars(Pageable pageable) {
        return avatarRepository.findAll(pageable);
    }
}
