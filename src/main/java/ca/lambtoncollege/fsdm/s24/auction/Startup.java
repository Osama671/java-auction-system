package ca.lambtoncollege.fsdm.s24.auction;

import ca.lambtoncollege.fsdm.s24.auction.db.Database;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class Startup implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        Database.Initialize();
    }
}
