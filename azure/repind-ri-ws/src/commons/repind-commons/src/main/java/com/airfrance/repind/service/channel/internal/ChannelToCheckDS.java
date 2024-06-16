package com.airfrance.repind.service.channel.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.channel.ChannelToCheckRepository;
import com.airfrance.repind.dto.channel.ChannelToCheckDTO;
import com.airfrance.repind.dto.channel.ChannelToCheckTransform;
import com.airfrance.repind.entity.channel.ChannelToCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ChannelToCheckDS {
	
	@Autowired
	private ChannelToCheckRepository channelToCheckRepository;

    @Transactional(readOnly=true)
	public ChannelToCheckDTO get(ChannelToCheckDTO dto) throws JrafDomainException {
		return get(dto.getId());
	}

    @Transactional(readOnly=true)
	public ChannelToCheckDTO get(Serializable oid) throws JrafDomainException {
		Optional<ChannelToCheck> channel = channelToCheckRepository.findById((Integer) oid);
		if (!channel.isPresent())
			return null;

		return ChannelToCheckTransform.bo2DtoLight(channel.get());
	}

	public List<ChannelToCheckDTO> findAll() throws JrafDomainException {
		List<ChannelToCheckDTO> channels = new ArrayList<>();
		for (ChannelToCheck found : channelToCheckRepository.findAll()) {
			channels.add(ChannelToCheckTransform.bo2DtoLight(found));
		}
		return channels;
	}
}
