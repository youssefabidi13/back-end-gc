package com.gi.gestioncompetence.controller;

import com.gi.gestioncompetence.dto.FeedBackDtoOriginal;
import com.gi.gestioncompetence.dto.FileDto;
import com.gi.gestioncompetence.entity.Department;
import com.gi.gestioncompetence.entity.Feedback;
import com.gi.gestioncompetence.entity.FileEntity;
import com.gi.gestioncompetence.entity.UserFisca;
import com.gi.gestioncompetence.repository.DepartementRepo;
import com.gi.gestioncompetence.repository.FileRepository;
import com.gi.gestioncompetence.repository.UserRepo;
import com.gi.gestioncompetence.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class FileController {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private EmailSenderService senderService;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DepartementRepo departmentRepo;

    // Endpoint pour gérer les demandes de téléchargement de fichier
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("department") String department,@RequestParam("id") Long id) {
        try {
            // Créer une entité de fichier pour stocker les informations du fichier
            FileEntity fileEntity = new FileEntity();

            // Définir le nom de fichier dans l'entité en utilisant le nom original du fichier
            fileEntity.setFilename(file.getOriginalFilename());

            // Définir le type de contenu dans l'entité en utilisant le type de contenu du fichier
            fileEntity.setContentType(file.getContentType());

            fileEntity.setDepartment(department);

            // Lire les données du fichier et les définir dans l'entité
            fileEntity.setData(file.getBytes());

            fileEntity.setUserFisca(userRepo.findById(id).orElse(null));

            // Enregistrer l'entité de fichier dans le référentiel (base de données, par exemple)
            fileRepository.save(fileEntity);
            triggerMail(fileEntity.getFilename());

            // Message de succès indiquant que le fichier a été téléchargé avec succès
            String message = "File uploaded successfully!";

            // Statut HTTP indiquant que la requête a été traitée avec succès
            HttpStatus httpStatus = HttpStatus.CREATED;

            // Retourner une réponse avec le message de succès et le statut HTTP correspondant
            return new ResponseEntity<>(message, httpStatus);
        } catch (IOException e) {
            // En cas d'erreur lors de la manipulation du fichier (lecture, écriture, etc.)
            // Retourner une réponse avec un statut HTTP interne du serveur (500)
            return ResponseEntity.status(500).build();
        }
    }

    // Endpoint pour récupérer la liste des fichiers
    @GetMapping("/files")
    public ResponseEntity<List<FileEntity>> getFile() {
        // Récupérer la liste de toutes les entités de fichiers à partir du référentiel
        List<FileEntity> files = fileRepository.findAll();

        // Retourner une réponse avec la liste de fichiers et le statut HTTP OK
        return ResponseEntity.ok(files);
    }

    // Endpoint pour télécharger un fichier par ID
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id) {
        // Rechercher l'entité de fichier par ID dans le référentiel
        FileEntity fileEntity = fileRepository.findById(id).orElse(null);

        // Vérifier si l'entité de fichier existe
        if (fileEntity != null) {
            // Préparer les en-têtes de la réponse
            HttpHeaders headers = new HttpHeaders();

            // Définir le type de contenu de la réponse en fonction du type de contenu du fichier
            headers.setContentType(MediaType.parseMediaType(fileEntity.getContentType()));

            // Définir l'en-tête Content-Disposition pour indiquer que la réponse est une pièce jointe
            headers.setContentDisposition(ContentDisposition.attachment().filename(fileEntity.getFilename()).build());

            // Créer une ressource ByteArrayResource à partir des données du fichier
            ByteArrayResource resource = new ByteArrayResource(fileEntity.getData());

            // Retourner une réponse OK avec les en-têtes et la ressource ByteArrayResource
            return ResponseEntity.ok().headers(headers).body(resource);
        } else {
            // Si l'entité de fichier n'est pas trouvée, retourner une réponse Not Found (404)
            return ResponseEntity.notFound().build();
        }
    }
    public void triggerMail(String fileName) {
        List<UserFisca> users = userRepo.findAll();

        for (UserFisca user : users) {
            String subject = "La Formation " + fileName + " est bien disponible";
            String body = "Bonjour " + user.getNomComplet() + ",\n\n" +
                    "Veuillez consulter la nouvelle formation qui vient d'être publiée sur notre plateforme:\n" +
                    "http://localhost:4200/\n\n" +
                    "Merci,\n" +
                    "From The Company Admin-Team";

            senderService.sendSimpleEmail(user.getEmail(), subject, body);
        }
    }

    @GetMapping("/departments")
    public List<String> getAllDepartmentNames() {
        List<Department> departments = departmentRepo.findAll();
        return departments.stream()
                .map(Department::getNomDepartement)
                .collect(Collectors.toList());
    }

    @GetMapping("/files/{department}")
    public List<FileEntity> getFilesByDepartment(@PathVariable String department) {
        //System.out.println(fileRepository.findByDepartment(department));
        return fileRepository.findByDepartment(department);

    }
    @GetMapping("/file-by-id/{id}")
    public FileDto getFileById(@PathVariable Long id) {
        FileEntity f = fileRepository.findById(id).orElse(null);
        FileDto fileDto = new FileDto();
        fileDto.setId(f.getId());
        fileDto.setFilename(f.getFilename());
        fileDto.setDepartment(f.getDepartment());
        fileDto.setContentType(f.getContentType());
        fileDto.setData(f.getData());
        fileDto.setFullName(f.getUserFisca().getNomComplet());
        String message_feedback;
        int rate;
        long file_id;
        String name;
        Date dateFeedback;
        System.out.println(f.getFeedbacks().size() +"hzllo");
        for (Feedback feedback : f.getFeedbacks()) {
            System.out.println(feedback.getMessageFeedback());
            message_feedback = feedback.getMessageFeedback();
            rate = feedback.getRate();
            file_id = feedback.getIdFeedback();
            name = feedback.getUtilisateur().getNomComplet();
            dateFeedback = feedback.getDateFeedback();
            fileDto.setFeedback(message_feedback, rate, file_id, name, dateFeedback);

        }
        return fileDto;
    }

}

