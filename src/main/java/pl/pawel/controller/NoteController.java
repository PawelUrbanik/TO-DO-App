package pl.pawel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pawel.model.Note;
import pl.pawel.repository.NoteRepository;

import java.util.List;

@Controller
public class NoteController {

    public static final Logger LOGGER = LoggerFactory.getLogger(NoteController.class);
    private NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @RequestMapping(value = "/notes", params = {"!sort", "!page", "!size"}, method = RequestMethod.GET)
    public ResponseEntity<List<Note>> readAllNotes(){
        LOGGER.warn("REQUEST GET ALL NOTES");
        return ResponseEntity.ok(noteRepository.findAll());
    }

    @RequestMapping(value = "/notes", method = RequestMethod.GET)
    public ResponseEntity<List<Note>> readAllNotes(Pageable pageable)
    {
         LOGGER.warn("REQUEST GET ALL NOTES WITH PARAMETER");
         return ResponseEntity.ok(noteRepository.findAll(pageable).getContent());
    }
}
