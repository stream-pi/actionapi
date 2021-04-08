package com.stream_pi.action_api.externalplugin;

import com.stream_pi.action_api.action.Action;
import com.stream_pi.action_api.action.ActionType;
import com.stream_pi.action_api.action.PropertySaver;
import com.stream_pi.action_api.actionproperty.ClientProperties;
import com.stream_pi.action_api.actionproperty.property.Property;
import com.stream_pi.action_api.actionproperty.property.Type;
import com.stream_pi.util.version.Version;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public abstract class ExternalPlugin extends Action
{
    private String author = "Unknown Author";

    public ExternalPlugin(String name, String author, String helpLink, Version version) {
        super(ActionType.NORMAL);
        setName(name);
        this.author = author;
        setHelpLink(helpLink);
        setVersion(version);
    }

    private boolean visibileInPluginsPane = true;
    private boolean visibileInServerSettingsPane = true;

    public ExternalPlugin(ActionType normal)
    {
        super(normal);
        setModuleName(getClass().getModule().getName());
    }



    public void setVisibilityInPluginsPane(boolean visibileInPluginsPane)
    {
        this.visibileInPluginsPane = visibileInPluginsPane;
    }

    public void setVisibilityInServerSettingsPane(boolean visibileInServerSettingsPane)
    {
        this.visibileInServerSettingsPane = visibileInServerSettingsPane;
    }

    public boolean isVisibleInPluginsPane()
    {
        return visibileInPluginsPane;
    }

    public boolean isVisibleInServerSettingsPane()
    {
        return visibileInServerSettingsPane;
    }


    public void addServerProperties(Property... properties)
    {
        for (Property property : properties)
        {
            if(property.getType() == Type.LIST || property.getType() == Type.INTEGER || property.getType() == Type.DOUBLE)
                property.setRawValue("0");
            else if(property.getType() == Type.BOOLEAN)
                property.setRawValue("false");
            else if(property.getType() == Type.STRING)
                property.setRawValue("");

            getServerProperties().addProperty(property);
        }
    }

    public void addClientProperties(Property... properties)
    {
        for (Property property : properties)
        {
            if(property.getType() == Type.LIST || property.getType() == Type.INTEGER || property.getType() == Type.DOUBLE)
                property.setRawValue("0");
            else if(property.getType() == Type.BOOLEAN)
                property.setRawValue("false");
            else if(property.getType() == Type.STRING)
                property.setRawValue("");

            getClientProperties().addProperty(property);
        }
    }


    public void setAuthor(String author)
    {
        this.author = author;
    }


    public String getAuthor()
    {
        return author;
    }


    public void initProperties() throws Exception
    {

    }


    public void initAction() throws Exception
    {

    }

    public void initClientActionSettingsButtonBar()
    {

    }

    public void onShutDown() throws Exception
    {
        //Runs when server shuts down
    }

    public ExternalPlugin clone() throws CloneNotSupportedException {
        ExternalPlugin action = (ExternalPlugin) super.clone();
        action.setClientProperties((ClientProperties) getClientProperties().clone());
        return action;
    }

    private VBox serverSettingsButtonBar = null;
    public void setServerSettingsButtonBar(Button... buttons)
    {
        serverSettingsButtonBar = new VBox(buttons);
        serverSettingsButtonBar.setSpacing(5.0);
    }

    public VBox getServerSettingsButtonBar()
    {
        return serverSettingsButtonBar;
    }

    private VBox clientActionSettingsButtonBar = null;
    public void setClientActionSettingsButtonBar(Button... buttons)
    {
        clientActionSettingsButtonBar = new VBox(buttons);
        clientActionSettingsButtonBar.setSpacing(5.0);
    }

    public VBox getClientActionSettingsButtonBar()
    {
        return clientActionSettingsButtonBar;
    }

    public void onActionCreate() throws Exception
    {
        // This method is called when the action is first created and send to the Client
    }

    public void onActionSavedFromServer() throws Exception
    {
        // This method is called when action is saved from the server
    }

    public void onActionDeleted() throws Exception
    {
        // This method is called when action is deleted from client.
    }

    public void onClientDisconnected() throws Exception
    {
        // This method is called when client is disconnected
    }

    private PropertySaver propertySaver = null;

    public void setPropertySaver(PropertySaver propertySaver)
    {
        this.propertySaver = propertySaver;
    }

    public void saveServerProperties()
    {
        propertySaver.saveServerProperties();
    }


    public void saveClientAction(boolean sendIcons, boolean runAsync)
    {
        propertySaver.saveClientAction(getProfileID(), getID(), getSocketAddressForClient(), sendIcons, runAsync);
    }

    public void saveClientAction()
    {
        propertySaver.saveClientAction(getProfileID(), getID(), getSocketAddressForClient(), true, false);
    }

    public void saveAllIcons()
    {
        propertySaver.saveAllIcons(getProfileID(), getID(), getSocketAddressForClient());
    }

    public void saveIcon(String state)
    {
        propertySaver.saveIcon(state, getProfileID(), getID(), getSocketAddressForClient());
    }
}
