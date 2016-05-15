/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pv168;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Radoslav Karlik (422358)
 */
@Configuration
@EnableTransactionManagement 
@PropertySource("classpath:myconf.properties") 

 public class SpringConfig {
    public static Logger log = LoggerFactory.getLogger(SpringConfig.class);
    
    @Autowired
    Environment env;
 
    @Bean
    public DataSource dataSource() {
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName(env.getProperty("jdbc.driver"));
        bds.setUrl(env.getProperty("jdbc.url"));
        bds.setUsername(env.getProperty("jdbc.user"));
        bds.setPassword(env.getProperty("jdbc.password"));
        return bds;
    }
    
    @Bean PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
    
    @Bean
    public RoomManager roomManager() {
        return new RoomManagerImpl(dataSource());
    }
 
    @Bean 
    public GuestManager guestManager() {
        return new GuestManagerImpl(dataSource());
    }
 
    @Bean
    public HotelManager hotelManager() {
        HotelManagerImpl hotelManager = new HotelManagerImpl(dataSource());
        hotelManager.setGuestManager(guestManager());
        hotelManager.setRoomManager(roomManager());
        return hotelManager;
    }
}
