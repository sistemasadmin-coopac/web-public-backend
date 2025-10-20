package com.elsalvador.coopac.service.admin.about;

import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Servicio para gestionar miembros de junta directiva
 */
public interface ManageAboutBoardService {

    /**
     * Crea un nuevo miembro de junta directiva
     */
    AboutAdminDTO.AboutBoardMemberDTO createBoardMember(AboutAdminDTO.AboutBoardMemberDTO dto, MultipartFile photo);

    /**
     * Actualiza un miembro de junta directiva existente
     */
    AboutAdminDTO.AboutBoardMemberDTO updateBoardMember(UUID id, AboutAdminDTO.AboutBoardMemberDTO dto, MultipartFile photo);

    /**
     * Elimina un miembro de junta directiva
     */
    void deleteBoardMember(UUID id);

    /**
     * Obtiene la configuración de la sección de junta directiva
     */
    AboutAdminDTO.AboutBoardSectionDTO getBoardSection();

    /**
     * Actualiza la configuración de la sección de junta directiva
     */
    AboutAdminDTO.AboutBoardSectionDTO updateBoardSection(AboutAdminDTO.AboutBoardSectionDTO dto);
}
