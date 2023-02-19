package hu.rekomo.service;

import hu.rekomo.DB.Database;
import hu.rekomo.model.UserData;
import hu.rekomo.util.Hash;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("auth")
public class Login {

    /**
     * Method handling HTTP GET requests.The returned object will be sent to the
     * client as "text/plain" media type.
     *
     * @param userdata
     * @return String that will be returned as a text/plain response.
     */
    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserData userdata) {
        try {
            Database db = new Database("root", "");
            String[] column = {"password"};
            Object[] where = {userdata.getUsername()};
            ResultSet rs = db.select("users", column, "username = ?", where);
            if(!rs.next()) {
                return Response.status(403).build();
            }
            String hash = rs.getString("password");
            if(!Hash.verify(hash, userdata.getPassword())) {
                return Response.status(403).build();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
}
