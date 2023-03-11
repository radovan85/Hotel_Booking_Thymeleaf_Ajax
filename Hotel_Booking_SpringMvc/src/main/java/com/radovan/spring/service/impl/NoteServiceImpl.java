package com.radovan.spring.service.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.NoteDto;
import com.radovan.spring.entity.NoteEntity;
import com.radovan.spring.repository.NoteRepository;
import com.radovan.spring.service.NoteService;

@Service
@Transactional
public class NoteServiceImpl implements NoteService{
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private TempConverter tempConverter;

	@Override
	public NoteDto getNoteById(Integer noteId) {
		// TODO Auto-generated method stub
		NoteDto returnValue = null;
		Optional<NoteEntity> noteEntity = Optional.ofNullable(noteRepository.getById(noteId));
		if(noteEntity.isPresent()) {
			returnValue = tempConverter.noteEntityToDto(noteEntity.get());
		}
		return returnValue;
	}

	@Override
	public void deleteNote(Integer noteId) {
		// TODO Auto-generated method stub
		noteRepository.deleteById(noteId);
		noteRepository.flush();
	}

	@Override
	public List<NoteDto> listAll() {
		// TODO Auto-generated method stub
		List<NoteDto> returnValue = new ArrayList<NoteDto>();
		Optional<List<NoteEntity>> allNotes = Optional.ofNullable(noteRepository.findAll());
		if(!allNotes.isEmpty()) {
			for(NoteEntity note:allNotes.get()) {
				NoteDto noteDto = tempConverter.noteEntityToDto(note);
				returnValue.add(noteDto);
			}
		}
		return returnValue;
	}

	@Override
	public List<NoteDto> listAllForToday() {
		// TODO Auto-generated method stub
		List<NoteDto> returnValue = new ArrayList<NoteDto>();
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String timestamp1Str = currentDate.format(formatter);
		timestamp1Str = timestamp1Str + " 00:00:00";
		String timestamp2Str = currentDate.format(formatter);
		timestamp2Str = timestamp2Str + " 23:59:59";
		
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		LocalDateTime dateTime1 = LocalDateTime.parse(timestamp1Str,formatter);
		Timestamp timestamp1 = Timestamp.valueOf(dateTime1);
		
		LocalDateTime dateTime2 = LocalDateTime.parse(timestamp2Str,formatter);
		Timestamp timestamp2 = Timestamp.valueOf(dateTime2);
		
		Optional<List<NoteEntity>> allNotes = 
				Optional.ofNullable(noteRepository.listAllForToday(timestamp1, timestamp2));
		if(!allNotes.isEmpty()) {
			for(NoteEntity note:allNotes.get()) {
				NoteDto noteDto = tempConverter.noteEntityToDto(note);
				returnValue.add(noteDto);
			}
		}
		
		
		return returnValue;
	}

	@Override
	public void deleteAllNotes() {
		// TODO Auto-generated method stub
		noteRepository.deleteAll();
		noteRepository.flush();
	}

}
