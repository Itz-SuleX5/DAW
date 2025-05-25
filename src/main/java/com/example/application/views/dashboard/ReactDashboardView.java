package com.example.application.views.dashboard;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("React Dashboard")
@Route("react-dashboard")
@Menu(order = 1, icon = LineAwesomeIconUrl.CHART_LINE_SOLID)
@JavaScript("./index.tsx")
@StyleSheet("./Dashboard.css")
@StyleSheet("./index.css")
public class ReactDashboardView extends Main {

    public ReactDashboardView() {
        addClassName("react-dashboard-view");
        
        // Create a container for the React component
        Div reactContainer = new Div();
        reactContainer.setId("react-dashboard-container");
        reactContainer.addClassName("react-container");
        
        // Add some inline JavaScript to initialize the React component
        String initScript = """
            <script>
                document.addEventListener('DOMContentLoaded', function() {
                    // Wait for React to be available
                    function initReactDashboard() {
                        if (window.ReactDashboard && window.ReactDashboard.render) {
                            const container = document.getElementById('react-dashboard-container');
                            if (container && !container.hasChildNodes()) {
                                window.ReactDashboard.render(container);
                            }
                        } else {
                            // Retry after a short delay
                            setTimeout(initReactDashboard, 100);
                        }
                    }
                    
                    // Start initialization
                    setTimeout(initReactDashboard, 50);
                });
            </script>
            """;
        
        Html scriptElement = new Html(initScript);
        
        add(reactContainer, scriptElement);
        
        // Set full size to allow React component to take full space
        setSizeFull();
        reactContainer.setSizeFull();
    }
}