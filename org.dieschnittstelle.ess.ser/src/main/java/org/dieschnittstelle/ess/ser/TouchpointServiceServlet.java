package org.dieschnittstelle.ess.ser;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.dieschnittstelle.ess.utils.Utils.*;

import org.apache.http.HttpResponse;
import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.crm.AbstractTouchpoint;

public class TouchpointServiceServlet extends HttpServlet {

    protected static Logger logger = org.apache.logging.log4j.LogManager
            .getLogger(TouchpointServiceServlet.class);

    public TouchpointServiceServlet() {
        show("TouchpointServiceServlet: constructor invoked\n");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {

        logger.info("doGet()");

        // we assume here that GET will only be used to return the list of all
        // touchpoints

        // obtain the executor for reading out the touchpoints
        TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext()
                .getAttribute("touchpointCRUD");
        try {
            // set the status
            response.setStatus(HttpServletResponse.SC_OK);
            // obtain the output stream from the response and write the list of
            // touchpoints into the stream
            ObjectOutputStream oos = new ObjectOutputStream(
                    response.getOutputStream());
            // write the object
            oos.writeObject(exec.readAllTouchpoints());
            oos.close();
        } catch (Exception e) {
            String err = "got exception: " + e;
            logger.error(err, e);
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) {

        // assume POST will only be used for touchpoint creation, i.e. there is
        // no need to check the uri that has been used

        // obtain the executor for reading out the touchpoints from the servlet context using the touchpointCRUD attribute
        TouchpointCRUDExecutor touchpointCRUDExecutor = (TouchpointCRUDExecutor) getServletContext().getAttribute("touchpointCRUD");

        try {
            // create an ObjectInputStream from the request's input stream
            ObjectInputStream reqObjectInputStream = new ObjectInputStream(request.getInputStream());

            // read an AbstractTouchpoint object from the stream
            AbstractTouchpoint reqTouchpoint = (AbstractTouchpoint) reqObjectInputStream.readObject();

            // call the create method on the executor and take its return value
            AbstractTouchpoint createdTouchpoint = touchpointCRUDExecutor.createTouchpoint(reqTouchpoint);

            // set the response status as successful, using the appropriate
            // constant from HttpServletResponse
            response.setStatus(HttpServletResponse.SC_CREATED);

            // then write the object to the response's output stream, using a
            // wrapping ObjectOutputStream
            ObjectOutputStream resObjectOutputStream = new ObjectOutputStream(response.getOutputStream());

            // ... and write the object to the stream
            resObjectOutputStream.writeObject(createdTouchpoint);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request,
                            HttpServletResponse response) {
        // obtain the executor for reading out the touchpoints from the servlet context using the touchpointCRUD attribute
        TouchpointCRUDExecutor touchpointCRUDExecutor = (TouchpointCRUDExecutor) getServletContext().getAttribute("touchpointCRUD");

        try {
            // read touchpoint ID from path info (e.g. "/42") and parse it as long
            String requestPathInfo = request.getPathInfo();
            long touchpointIdFromPath = Long.parseLong(requestPathInfo.substring(1));

            // call the create method on the executor and take its return value
            boolean deletedTouchpoint = touchpointCRUDExecutor.deleteTouchpoint(touchpointIdFromPath);

            if (deletedTouchpoint) {
                // set the response status as successful, using the appropriate
                // constant from HttpServletResponse
                response.setStatus(HttpServletResponse.SC_OK);

                // then write the object to the response's output stream, using a
                // wrapping ObjectOutputStream
                ObjectOutputStream resObjectOutputStream = new ObjectOutputStream(response.getOutputStream());

                // ... and write the ID of the deleted touchpoint to the stream
                resObjectOutputStream.writeObject(touchpointIdFromPath);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
