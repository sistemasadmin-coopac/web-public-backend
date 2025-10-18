package com.elsalvador.coopac.service.admin.contact.impl;

import com.elsalvador.coopac.dto.admin.ContactAdminDTO;
import com.elsalvador.coopac.entity.contact.ContactChannels;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.SiteSettingsRepository;
import com.elsalvador.coopac.repository.contact.ContactChannelsRepository;
import com.elsalvador.coopac.service.admin.contact.UpdateContactChannelsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.CONTACT_PAGE_CACHE;
import static com.elsalvador.coopac.config.CacheConfig.FOOTER_PAGE_CACHE;

import java.util.UUID;

/**
 * Implementación del servicio para actualizar canales de contacto
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UpdateContactChannelsServiceImpl implements UpdateContactChannelsService {

    private final ContactChannelsRepository contactChannelsRepository;
    private final SiteSettingsRepository siteSettingsRepository;

    @Override
    @CacheEvict(value = {CONTACT_PAGE_CACHE, FOOTER_PAGE_CACHE}, allEntries = true)
    public ContactAdminDTO.ContactChannelItemDTO updateContactChannel(UUID id, ContactAdminDTO.UpdateContactChannelDTO updateDTO) {
        log.info("Actualizando canal de contacto con ID: {}", id);

        ContactChannels channel = contactChannelsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Canal de contacto no encontrado con ID: " + id));

        if (updateDTO.icon() != null) {
            channel.setIcon(updateDTO.icon());
        }
        if (updateDTO.label() != null) {
            channel.setLabel(updateDTO.label());
        }
        if (updateDTO.description() != null) {
            channel.setDescription(updateDTO.description());
        }
        if (updateDTO.useGlobalValue() != null) {
            channel.setUseGlobalValue(updateDTO.useGlobalValue());
        }
        if (updateDTO.customValue() != null) {
            channel.setCustomValue(updateDTO.customValue());
        }
        if (updateDTO.displayOrder() != null) {
            channel.setDisplayOrder(updateDTO.displayOrder());
        }
        if (updateDTO.isActive() != null) {
            channel.setIsActive(updateDTO.isActive());
        }

        ContactChannels updatedChannel = contactChannelsRepository.save(channel);
        log.info("Canal de contacto actualizado exitosamente");

        return mapToDTO(updatedChannel);
    }

    /**
     * Mapea entidad a DTO
     */
    private ContactAdminDTO.ContactChannelItemDTO mapToDTO(ContactChannels channel) {
        String value = channel.getUseGlobalValue() ?
                getGlobalValue(channel.getChannelType()) :
                channel.getCustomValue();

        return new ContactAdminDTO.ContactChannelItemDTO(
                channel.getId(),
                channel.getChannelType(),
                channel.getIcon(),
                channel.getLabel(),
                value,
                channel.getDescription(),
                channel.getDisplayOrder(),
                channel.getUseGlobalValue(),
                channel.getCustomValue(),
                channel.getIsActive()
        );
    }

    /**
     * Obtiene el valor global de configuración para un tipo de canal
     */
    private String getGlobalValue(String channelType) {
        return switch (channelType.toLowerCase()) {
            case "phone" -> siteSettingsRepository.findFirstByOrderByUpdatedAtDesc()
                    .map(settings -> settings.getPhoneMain())
                    .orElse("+503 2555-1234");
            case "whatsapp" -> siteSettingsRepository.findFirstByOrderByUpdatedAtDesc()
                    .map(settings -> settings.getWhatsappNumber())
                    .orElse("+503 7890-1234");
            case "email" -> siteSettingsRepository.findFirstByOrderByUpdatedAtDesc()
                    .map(settings -> settings.getEmailMain())
                    .orElse("info@coopac-elsalvador.com");
            case "location" -> siteSettingsRepository.findFirstByOrderByUpdatedAtDesc()
                    .map(settings -> settings.getAddressLine1())
                    .orElse("Av. Los Héroes, San Salvador");
            default -> "";
        };
    }
}
