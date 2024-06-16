package com.airfrance.repind.dao.channel;

import com.airfrance.repind.entity.channel.ChannelToCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelToCheckRepository extends JpaRepository<ChannelToCheck, Integer> {
}
