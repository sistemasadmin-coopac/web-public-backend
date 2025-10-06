package com.elsalvador.coopac.service.publicpages.mapper.contact;

import com.elsalvador.coopac.dto.publicpage.contact.ContactPageDTO;
import com.elsalvador.coopac.entity.contact.ContactChannels;
import com.elsalvador.coopac.entity.contact.ContactLocations;
import com.elsalvador.coopac.entity.contact.ContactScheduleEntries;
import com.elsalvador.coopac.entity.page.PageHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entidades de Contact a DTOs
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ContactMapper {

    /**
     * Mapea el header de Contact usando PageHeaders
     */
    public ContactPageDTO.HeaderDTO mapHeader(PageHeaders pageHeader) {
        return new ContactPageDTO.HeaderDTO(
                pageHeader.getTitleMain(),
                pageHeader.getSubtitle()
        );
    }

    /**
     * Mapea la información de contacto con canales
     */
    public ContactPageDTO.ContactInfoDTO mapContactInfo(List<ContactChannels> channels, String title) {
        var items = channels.stream()
                .map(this::mapContactChannel)
                .collect(Collectors.toList());

        return new ContactPageDTO.ContactInfoDTO(
                title != null ? title : "Información de Contacto",
                items
        );
    }

    /**
     * Mapea un canal de contacto individual
     */
    private ContactPageDTO.ContactItemDTO mapContactChannel(ContactChannels channel) {
        String value = channel.getUseGlobalValue() ?
                "" : // El valor global debe venir del servicio, no del mapper
                channel.getCustomValue();

        return new ContactPageDTO.ContactItemDTO(
                channel.getChannelType(),
                channel.getIcon(),
                channel.getLabel(),
                value,
                channel.getDescription(),
                channel.getDisplayOrder()
        );
    }

    /**
     * Mapea la sección de horarios
     */
    public ContactPageDTO.ScheduleDTO mapSchedule(List<ContactScheduleEntries> scheduleEntries, String title, String note) {
        var items = scheduleEntries.stream()
                .map(this::mapScheduleEntry)
                .collect(Collectors.toList());

        return new ContactPageDTO.ScheduleDTO(
                title != null ? title : "Horarios de Atención",
                items,
                note != null ? note : ""
        );
    }

    /**
     * Mapea una entrada de horario individual
     */
    private ContactPageDTO.ScheduleItemDTO mapScheduleEntry(ContactScheduleEntries entry) {
        return new ContactPageDTO.ScheduleItemDTO(
                entry.getLabel(),
                entry.getIsClosed() ? "" : (entry.getOpenTime() != null ? entry.getOpenTime().toString() : ""),
                entry.getIsClosed() ? "" : (entry.getCloseTime() != null ? entry.getCloseTime().toString() : ""),
                entry.getIsClosed(),
                entry.getDisplayOrder()
        );
    }

    /**
     * Mapea la sección de ubicaciones
     */
    public ContactPageDTO.LocationDTO mapLocation(List<ContactLocations> locations, String title, String subtitle) {
        var places = locations.stream()
                .map(this::mapLocationPlace)
                .collect(Collectors.toList());

        return new ContactPageDTO.LocationDTO(
                title != null ? title : "Ubicaciones",
                subtitle != null ? subtitle : "",
                places
        );
    }

    /**
     * Mapea una ubicación individual
     */
    private ContactPageDTO.LocationPlaceDTO mapLocationPlace(ContactLocations location) {
        String phone = location.getPhoneOverride();
        String whatsapp = location.getWhatsappOverride();

        var map = new ContactPageDTO.MapDTO(
                location.getLatitude(),
                location.getLongitude(),
                15
        );

        var actions = List.of(
                new ContactPageDTO.ActionDTO("Llamar", "phone",
                        phone != null ? phone.replaceAll("[^0-9+]", "") : "", false, 0),
                new ContactPageDTO.ActionDTO("WhatsApp", "whatsapp",
                        whatsapp != null ? "https://wa.me/" + whatsapp.replaceAll("[^0-9]", "") : "", true, 1)
        );

        return new ContactPageDTO.LocationPlaceDTO(
                location.getName(),
                location.getAddress(),
                phone,
                whatsapp,
                location.getScheduleLabel(),
                map,
                actions
        );
    }
}
