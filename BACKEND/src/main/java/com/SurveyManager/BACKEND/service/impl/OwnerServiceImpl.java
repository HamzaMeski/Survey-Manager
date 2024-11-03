package com.SurveyManager.BACKEND.service.impl;

import com.SurveyManager.BACKEND.dto.request.OwnerRequestDTO;
import com.SurveyManager.BACKEND.dto.response.OwnerResponseDTO;
import com.SurveyManager.BACKEND.entity.Owner;
import com.SurveyManager.BACKEND.exception.ResourceNotFoundException;
import com.SurveyManager.BACKEND.exception.DuplicateResourceException;
import com.SurveyManager.BACKEND.mapper.OwnerMapper;
import com.SurveyManager.BACKEND.repository.OwnerRepository;
import com.SurveyManager.BACKEND.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {
    
    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;
    
    @Override
    @Transactional
    public OwnerResponseDTO create(OwnerRequestDTO requestDTO) {
        if (ownerRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + requestDTO.getEmail());
        }
        
        Owner owner = ownerMapper.toEntity(requestDTO);
        owner = ownerRepository.save(owner);
        return ownerMapper.toResponseDTO(owner);
    }
    
    @Override
    @Transactional(readOnly = true)
    public OwnerResponseDTO getById(Long id) {
        Owner owner = ownerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + id));
        return ownerMapper.toResponseDTO(owner);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<OwnerResponseDTO> getAll() {
        return ownerRepository.findAll().stream()
            .map(ownerMapper::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public OwnerResponseDTO update(Long id, OwnerRequestDTO requestDTO) {
        Owner owner = ownerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + id));
            
        if (!owner.getEmail().equals(requestDTO.getEmail()) && 
            ownerRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + requestDTO.getEmail());
        }
        
        ownerMapper.updateEntity(owner, requestDTO);
        owner = ownerRepository.save(owner);
        return ownerMapper.toResponseDTO(owner);
    }
    
    @Override
    @Transactional
    public void delete(Long id) {
        if (!ownerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Owner not found with id: " + id);
        }
        ownerRepository.deleteById(id);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return ownerRepository.existsByEmail(email);
    }
} 