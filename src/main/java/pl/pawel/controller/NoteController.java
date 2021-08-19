package pl.pawel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import pl.pawel.model.Note;
import pl.pawel.repository.NoteRepository;

import java.util.List;

@RepositoryRestController
public class NoteController {

    public static final Logger LOGGER = LoggerFactory.getLogger(NoteController.class);
    private NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping(value = "/notes", params = {"!sort", "!page", "!size"})
    public ResponseEntity<List<Note>> readAllNotes(){
        LOGGER.warn("REQUEST GET ALL NOTES");
        return ResponseEntity.ok(noteRepository.findAll());
    }

    @GetMapping("/notes")
    public ResponseEntity<List<Note>> readAllNotes(Pageable pageable)
    {
         LOGGER.warn("REQUEST GET ALL NOTES WITH PARAMETER");
         return ResponseEntity.ok(noteRepository.findAll(pageable).getContent());
    }
}
