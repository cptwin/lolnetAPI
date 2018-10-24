package nz.co.lolnet.lolnetapi.lolcon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.rmi.NoSuchObjectException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import nz.co.lolnet.lolnetapi.APIKeyNotSetException;
import nz.co.lolnet.lolnetapi.settings.Settings;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author cptwin
 */
public class lolCon {

    @Deprecated
    public static boolean registerNewPlayer(String authHash, String playername) throws UnsupportedEncodingException, MalformedURLException, IOException, ParseException {
        return registerNewPlayer(authHash, playername, null);
    }

    public static boolean registerNewPlayer(String authHash, String playername, UUID playeruuid) throws UnsupportedEncodingException, MalformedURLException, IOException, ParseException {
        if (playerExists(authHash, playername)) {
            return true;
        } else {
            boolean result = false;
            try {
                // Construct data

                String data = URLEncoder.encode("playername", "UTF-8") + "=" + URLEncoder.encode(playername, "UTF-8");
                if (playeruuid != null) {
                    data += "&" + URLEncoder.encode("playeruuid", "UTF-8") + "=" + URLEncoder.encode(playeruuid.toString(), "UTF-8");
                } else {
                    data += "&" + URLEncoder.encode("playeruuid", "UTF-8") + "=" + URLEncoder.encode("NA", "UTF-8");
                }
                data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");
                // Send data
                URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/registernewplayer_new.php");
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                conn.setConnectTimeout(Settings.connectTimeout);
                conn.setReadTimeout(Settings.readTimeout);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                // Get the response
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                while ((line = rd.readLine()) != null) {
                    if (line.toLowerCase().contains("true")) {
                        result = true;
                        break;
                    }
                }
                wr.close();
                rd.close();
            } catch (Exception e) {
            }
            return result;
        }
    }

    public static boolean ChangePlayerUUID(String playername, UUID playeruuid, String authHash) throws UnsupportedEncodingException, MalformedURLException, IOException, ParseException {
        if (!playerExists(authHash, playername)) {
            return false;
        } else {
            boolean result = false;
            try {
                // Construct data

                String data = URLEncoder.encode("playername", "UTF-8") + "=" + URLEncoder.encode(playername, "UTF-8");
                data += "&" + URLEncoder.encode("playeruuid", "UTF-8") + "=" + URLEncoder.encode(playeruuid.toString(), "UTF-8");
                data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");
                // Send data
                URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/changeplayeruuid.php");
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                conn.setConnectTimeout(Settings.connectTimeout);
                conn.setReadTimeout(Settings.readTimeout);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                // Get the response
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                while ((line = rd.readLine()) != null) {
                    if (line.toLowerCase().contains("true")) {
                        result = true;
                        break;
                    }
                }
                wr.close();
                rd.close();
            } catch (Exception e) {
                return result;
            }
            return result;
        }
    }

    public static boolean ChangePlayerName(String playername, UUID playeruuid, String authHash) throws UnsupportedEncodingException, MalformedURLException, IOException, ParseException {
        if (!playerExists(authHash, playeruuid)) {
            return false;
        } else {
            boolean result = false;
            try {
                // Construct data

                String data = URLEncoder.encode("playername", "UTF-8") + "=" + URLEncoder.encode(playername, "UTF-8");
                data += "&" + URLEncoder.encode("playeruuid", "UTF-8") + "=" + URLEncoder.encode(playeruuid.toString(), "UTF-8");
                data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");
                // Send data
                URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/changeplayername.php");
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                conn.setConnectTimeout(Settings.connectTimeout);
                conn.setReadTimeout(Settings.readTimeout);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                // Get the response
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                while ((line = rd.readLine()) != null) {
                    if (line.toLowerCase().contains("true")) {
                        result = true;
                        break;
                    }
                }
                wr.close();
                rd.close();
            } catch (Exception e) {
                return result;
            }
            return result;
        }
    }

    public static long getPlayerBalance(String playername) throws UnsupportedEncodingException, IOException, ParseException {
        String data = URLEncoder.encode("playername", "UTF-8") + "=" + URLEncoder.encode(playername, "UTF-8");

        URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/getplayerbalance.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(Settings.connectTimeout);
        conn.setReadTimeout(Settings.readTimeout);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONObject json = (JSONObject) new JSONParser().parse(rd.readLine());

        wr.close();
        rd.close();

        return (long) json.get("playerbalance");
    }

    public static double getPlayerVoteValueMultiplier(String playername) throws UnsupportedEncodingException, IOException, org.json.simple.parser.ParseException {
        String data = URLEncoder.encode("playername", "UTF-8") + "=" + URLEncoder.encode(playername, "UTF-8");

        URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/getplayervotevaluemultiplier.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(Settings.connectTimeout);
        conn.setReadTimeout(Settings.readTimeout);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONObject json = (JSONObject) new JSONParser().parse(rd.readLine());

        wr.close();
        rd.close();

        return Double.parseDouble(json.get("playermultiplier").toString());
    }

    public static double getPlayerVoteValueMultiplier2(String playername) throws UnsupportedEncodingException, IOException, org.json.simple.parser.ParseException {
        String data = URLEncoder.encode("playername", "UTF-8") + "=" + URLEncoder.encode(playername, "UTF-8");

        URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/getplayervotevaluemultiplier2.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(Settings.connectTimeout);
        conn.setReadTimeout(Settings.readTimeout);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONObject json = (JSONObject) new JSONParser().parse(rd.readLine());

        wr.close();
        rd.close();

        return Double.parseDouble(json.get("playermultiplier").toString());
    }

    public static boolean setPlayerVoteValueMultiplier(String authHash, String playerName, double ammount) {
        try {
            authHash = Settings.checkAPIKey(authHash);
        } catch (APIKeyNotSetException ex) {
            Logger.getLogger(lolCon.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        boolean result = false;
        try {
            // Construct data

            String data = URLEncoder.encode("playername", "UTF-8") + "=" + URLEncoder.encode(playerName, "UTF-8");
            data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");
            data += "&" + URLEncoder.encode("ammount", "UTF-8") + "=" + URLEncoder.encode(Double.toString(ammount), "UTF-8");

            // Send data
            URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/setplayervotevaluemultiplier.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(Settings.connectTimeout);
            conn.setReadTimeout(Settings.readTimeout);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = rd.readLine()) != null) {
                if (line.toLowerCase().contains("true")) {
                    result = true;
                    break;
                }
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    public static boolean setPlayerVoteValueMultiplier2(String authHash, String playerName, double ammount) {
        try {
            authHash = Settings.checkAPIKey(authHash);
        } catch (APIKeyNotSetException ex) {
            Logger.getLogger(lolCon.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        boolean result = false;
        try {
            // Construct data

            String data = URLEncoder.encode("playername", "UTF-8") + "=" + URLEncoder.encode(playerName, "UTF-8");
            data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");
            data += "&" + URLEncoder.encode("ammount", "UTF-8") + "=" + URLEncoder.encode(Double.toString(ammount), "UTF-8");

            // Send data
            URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/setplayervotevaluemultiplier2.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(Settings.connectTimeout);
            conn.setReadTimeout(Settings.readTimeout);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = rd.readLine()) != null) {
                if (line.toLowerCase().contains("true")) {
                    result = true;
                    break;
                }
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    public static long getPlayerBonusClaimBlocks(String playername) throws UnsupportedEncodingException, IOException, ParseException {
        String data = URLEncoder.encode("playername", "UTF-8") + "=" + URLEncoder.encode(playername, "UTF-8");

        URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/getplayerbonusclaimblocks.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(Settings.connectTimeout);
        conn.setReadTimeout(Settings.readTimeout);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONObject json = (JSONObject) new JSONParser().parse(rd.readLine());

        wr.close();
        rd.close();

        return (long) json.get("playerbonusclaimblocks");
    }

    public static String getPlayerTitle(String playername) throws UnsupportedEncodingException, IOException, ParseException {
        String data = URLEncoder.encode("playername", "UTF-8") + "=" + URLEncoder.encode(playername, "UTF-8");

        URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/getplayertitle.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(Settings.connectTimeout);
        conn.setReadTimeout(Settings.readTimeout);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONObject json = (JSONObject) new JSONParser().parse(rd.readLine());

        wr.close();
        rd.close();

        return (String) json.get("playerTitle");
    }

    public static boolean updatetPlayerTitle(String playerName, String newTitle, String authHash) {
        boolean result = false;
        try {
            // Construct data

            String data = URLEncoder.encode("playername", "UTF-8") + "=" + URLEncoder.encode(playerName, "UTF-8");
            data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");
            data += "&" + URLEncoder.encode("newtitle", "UTF-8") + "=" + URLEncoder.encode(newTitle, "UTF-8");
            // Send data
            URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/updateplayertitle.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(Settings.connectTimeout);
            conn.setReadTimeout(Settings.readTimeout);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = rd.readLine()) != null) {
                if (line.toLowerCase().contains("true")) {
                    result = true;
                    break;
                }
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    public static String getPlayerNick(String playername) throws UnsupportedEncodingException, IOException, ParseException {
        String data = URLEncoder.encode("playername", "UTF-8") + "=" + URLEncoder.encode(playername, "UTF-8");

        URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/getplayernick.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(Settings.connectTimeout);
        conn.setReadTimeout(Settings.readTimeout);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONObject json = (JSONObject) new JSONParser().parse(rd.readLine());

        wr.close();
        rd.close();

        return (String) json.get("playerNickname");
    }

    public static String getPlayerName(UUID playerUUID) throws UnsupportedEncodingException, IOException, ParseException {
        String data = URLEncoder.encode("playeruuid", "UTF-8") + "=" + URLEncoder.encode(playerUUID.toString(), "UTF-8");

        URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/getplayernamefromuuid.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(Settings.connectTimeout);
        conn.setReadTimeout(Settings.readTimeout);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONObject json = (JSONObject) new JSONParser().parse(rd.readLine());

        wr.close();
        rd.close();

        return (String) json.get("playername");
    }

    public static String getPlayerUUID(String playername) throws UnsupportedEncodingException, IOException, ParseException {
        String data = URLEncoder.encode("playername", "UTF-8") + "=" + URLEncoder.encode(playername, "UTF-8");

        URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/getplayeruuidfromname.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(Settings.connectTimeout);
        conn.setReadTimeout(Settings.readTimeout);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONObject json = (JSONObject) new JSONParser().parse(rd.readLine());

        wr.close();
        rd.close();

        return (String) json.get("playeruuid");
    }

    public static String getPlayerNameFromFourmID(int userForumID) throws UnsupportedEncodingException, IOException, ParseException {
        String data = URLEncoder.encode("playerid", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userForumID), "UTF-8");

        URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/getplayerfromforumid.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(Settings.connectTimeout);
        conn.setReadTimeout(Settings.readTimeout);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONObject json = (JSONObject) new JSONParser().parse(rd.readLine());

        wr.close();
        rd.close();

        return (String) json.get("playerName");
    }

    public static String getPlayerNameFromNick(String playerNick) throws UnsupportedEncodingException, IOException, ParseException {
        String data = URLEncoder.encode("playername", "UTF-8") + "=" + URLEncoder.encode(playerNick, "UTF-8");

        URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/getplayerfromnick.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(Settings.connectTimeout);
        conn.setReadTimeout(Settings.readTimeout);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONObject json = (JSONObject) new JSONParser().parse(rd.readLine());

        wr.close();
        rd.close();

        return (String) json.get("playerName");
    }

    public static boolean updatetPlayerNick(String playerName, String newNick, String authHash) {
        boolean result = false;
        try {
            // Construct data

            String data = URLEncoder.encode("playername", "UTF-8") + "=" + URLEncoder.encode(playerName, "UTF-8");
            data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");
            data += "&" + URLEncoder.encode("newnick", "UTF-8") + "=" + URLEncoder.encode(newNick, "UTF-8");
            // Send data
            URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/updateplayernick.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(Settings.connectTimeout);
            conn.setReadTimeout(Settings.readTimeout);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = rd.readLine()) != null) {
                if (line.toLowerCase().contains("true")) {
                    result = true;
                    break;
                }
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    public static HashMap<String, Long> getTop10MonthlyVoters() throws IOException, ParseException {
        HashMap<String, Long> output = new HashMap<>();

        // Send data
        URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/gettop10monthlyvotes.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(Settings.connectTimeout);
        conn.setReadTimeout(Settings.readTimeout);

        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONObject json = (JSONObject) new JSONParser().parse(rd.readLine());
        rd.close();

        Iterator<?> keys = json.keySet().iterator();
        while (keys.hasNext()) {
            String username = (String) keys.next();
            long votenum = (long) json.get(username);
            output.put(username, votenum);
        }

        return output;
    }

    @Deprecated
    public static HashMap<String, Integer> getForumGroups(String authHash) throws UnsupportedEncodingException, MalformedURLException, IOException, ParseException {
        return getForumGroupsNameKey(authHash);
    }

    public static HashMap<String, Integer> getForumGroupsNameKey(String authHash) throws UnsupportedEncodingException, MalformedURLException, IOException, ParseException {
        HashMap<String, Integer> output = new HashMap<>();
        try {
            authHash = Settings.checkAPIKey(authHash);
        } catch (APIKeyNotSetException ex) {
            Logger.getLogger(lolCon.class.getName()).log(Level.SEVERE, null, ex);
            return output;
        }
        String data = URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(authHash, "UTF-8");

        // Send data
        URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/getforumgroups.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(Settings.connectTimeout);
        conn.setReadTimeout(Settings.readTimeout);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONObject json = (JSONObject) new JSONParser().parse(rd.readLine());

        wr.close();
        rd.close();

        Iterator<?> keys = json.keySet().iterator();

        while (keys.hasNext()) {
            String group_id = (String) keys.next();
            String group_name = (String) json.get(group_id);
            output.put(group_name, Integer.parseInt(group_id));
        }

        return output;
    }

    public static HashMap<Integer, String> getForumGroupsIDKey(String authHash) throws UnsupportedEncodingException, MalformedURLException, IOException, ParseException {
        HashMap<Integer, String> output = new HashMap<>();

        try {
            authHash = Settings.checkAPIKey(authHash);
        } catch (APIKeyNotSetException ex) {
            Logger.getLogger(lolCon.class.getName()).log(Level.SEVERE, null, ex);
            return output;
        }

        String data = URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(authHash, "UTF-8");
        // Send data
        URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/getforumgroups.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(Settings.connectTimeout);
        conn.setReadTimeout(Settings.readTimeout);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONObject json = (JSONObject) new JSONParser().parse(rd.readLine());

        wr.close();
        rd.close();

        Iterator<?> keys = json.keySet().iterator();

        while (keys.hasNext()) {
            try {
                String group_id = (String) keys.next();
                String group_name = (String) json.get(group_id);
                output.put(Integer.parseInt(group_id), group_name);
            } catch (NullPointerException e) {
            }
        }

        return output;
    }

    public static int getForumUserID(String authHash, String username) throws UnsupportedEncodingException, MalformedURLException, IOException, ParseException {
        try {
            authHash = Settings.checkAPIKey(authHash);
        } catch (APIKeyNotSetException ex) {
            Logger.getLogger(lolCon.class.getName()).log(Level.SEVERE, null, ex);
            throw new NoSuchObjectException("API key not set");
        }
        String data = URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(authHash, "UTF-8");
        data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");

        // Send data
        URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/getforumuserid.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(Settings.connectTimeout);
        conn.setReadTimeout(Settings.readTimeout);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONObject json = (JSONObject) new JSONParser().parse(rd.readLine());

        wr.close();
        rd.close();

        Iterator<?> keys = json.keySet().iterator();

        while (keys.hasNext()) {
            String user = (String) keys.next();
            long forumid = (long) json.get(user);
            return safeLongToInt(forumid);
        }
        throw new NoSuchObjectException("Username not found in Database!");
    }

    public static ArrayList<Integer> getForumUserForumGroups(String authHash, int userForumID) throws UnsupportedEncodingException, MalformedURLException, IOException, ParseException {
        ArrayList<Integer> output = new ArrayList<>();
        try {
            authHash = Settings.checkAPIKey(authHash);
        } catch (APIKeyNotSetException ex) {
            Logger.getLogger(lolCon.class.getName()).log(Level.SEVERE, null, ex);
            return output;
        }

        String data = URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(authHash, "UTF-8");
        data += "&" + URLEncoder.encode("userforumid", "UTF-8") + "=" + URLEncoder.encode(userForumID + "", "UTF-8");

        // Send data
        URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/getforumuserforumgroups.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(Settings.connectTimeout);
        conn.setReadTimeout(Settings.readTimeout);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONArray json = (JSONArray) new JSONParser().parse(rd.readLine());

        wr.close();
        rd.close();

        for (Object o : json) {
            output.add(Integer.parseInt(o.toString()));
        }

        return output;
    }

    public static boolean addUserToForumGroup(String authHash, String playerName, int groupID) throws MalformedURLException, IOException, UnsupportedEncodingException, ParseException {
        boolean success = false;
        try {
            authHash = Settings.checkAPIKey(authHash);
        } catch (APIKeyNotSetException ex) {
            Logger.getLogger(lolCon.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        int playerForumID = getForumUserID(authHash, playerName);
        if (!userAlreadyBelongsToGroup(authHash, playerForumID, groupID)) {
            String data = URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(authHash, "UTF-8");
            data += "&" + URLEncoder.encode("groupid", "UTF-8") + "=" + URLEncoder.encode(groupID + "", "UTF-8");
            data += "&" + URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(playerForumID + "", "UTF-8");

            URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/addusertoforumgroup.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(Settings.connectTimeout);
            conn.setReadTimeout(Settings.readTimeout);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONObject json = (JSONObject) new JSONParser().parse(rd.readLine());
            success = (boolean) json.get("success");

            wr.close();
            rd.close();
        }
        return success;
    }

    public static boolean removeUserFromForumGroup(String authHash, String playerName, int groupID) throws MalformedURLException, IOException, UnsupportedEncodingException, ParseException {
        boolean success = false;
        try {
            authHash = Settings.checkAPIKey(authHash);
        } catch (APIKeyNotSetException ex) {
            Logger.getLogger(lolCon.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        int playerForumID = getForumUserID(authHash, playerName);
        if (userAlreadyBelongsToGroup(authHash, playerForumID, groupID)) {
            String data = URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(authHash, "UTF-8");
            data += "&" + URLEncoder.encode("groupid", "UTF-8") + "=" + URLEncoder.encode(groupID + "", "UTF-8");
            data += "&" + URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(playerForumID + "", "UTF-8");

            URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/removeuserfromforumgroup.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(Settings.connectTimeout);
            conn.setReadTimeout(Settings.readTimeout);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONObject json = (JSONObject) new JSONParser().parse(rd.readLine());
            success = (boolean) json.get("success");

            wr.close();
            rd.close();
        }

        return success;
    }

    public static long getLastVoted(String authHash, String playerName, String serverName, String serviceName) {
        try {
            authHash = Settings.checkAPIKey(authHash);
        } catch (APIKeyNotSetException ex) {
            Logger.getLogger(lolCon.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        try {
            // Construct data

            String data = URLEncoder.encode("playerName", "UTF-8") + "=" + URLEncoder.encode(playerName, "UTF-8");
            data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(authHash, "UTF-8");
            data += "&" + URLEncoder.encode("serverName", "UTF-8") + "=" + URLEncoder.encode(serverName, "UTF-8");
            data += "&" + URLEncoder.encode("serviceName", "UTF-8") + "=" + URLEncoder.encode(serviceName, "UTF-8");
            // Send data
            URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/gettimelastvoted.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(Settings.connectTimeout);
            conn.setReadTimeout(Settings.readTimeout);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONArray json = (JSONArray) new JSONParser().parse(rd.readLine());

            wr.close();
            rd.close();

            for (Object o : json) {
                String toString = o.toString();
                if (toString.length() <= 5) {
                    return 0;
                }
                return (Timestamp.valueOf(toString).getTime());
            }

        } catch (IOException | ParseException | IllegalArgumentException e) {
        }

        return 0;
    }

    private static boolean userAlreadyBelongsToGroup(String authHash, int playerID, int groupID) throws MalformedURLException, IOException, UnsupportedEncodingException, ParseException {
        boolean isInGroup = false;
        for (int groups : getForumUserForumGroups(authHash, playerID)) {
            if (groups == groupID) {
                isInGroup = true;
            }
        }
        return isInGroup;
    }

    private static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }

    public static boolean updatetPlayerBonusClaimBlocks(String authHash, String playerName, int balanceChange) {
        boolean result = false;
        try {
            // Construct data

            String data = URLEncoder.encode("playername", "UTF-8") + "=" + URLEncoder.encode(playerName, "UTF-8");
            data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");
            data += "&" + URLEncoder.encode("balancechange", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(balanceChange), "UTF-8");
            // Send data
            URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/updateplayerbonusclaimblocks.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(Settings.connectTimeout);
            conn.setReadTimeout(Settings.readTimeout);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = rd.readLine()) != null) {
                if (line.toLowerCase().contains("true")) {
                    result = true;
                    break;
                }
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    public static boolean playerExists(String authHash, String playerName) {
        boolean result = false;
        try {
            // Construct data

            String data = URLEncoder.encode("playername", "UTF-8") + "=" + URLEncoder.encode(playerName, "UTF-8");
            data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");
            // Send data
            URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/playerexistsinlolcoindatabase.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(Settings.connectTimeout);
            conn.setReadTimeout(Settings.readTimeout);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = rd.readLine()) != null) {
                if (line.toLowerCase().contains("true")) {
                    result = true;
                    break;
                }
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
        }
        return result;
    }

    public static boolean playerExists(String authHash, UUID playerUUID) {
        boolean result = false;
        try {
            // Construct data

            String data = URLEncoder.encode("playeruuid", "UTF-8") + "=" + URLEncoder.encode(playerUUID.toString(), "UTF-8");
            data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");
            // Send data
            URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/playerexistsinlolcoindatabaseuuid.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(Settings.connectTimeout);
            conn.setReadTimeout(Settings.readTimeout);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = rd.readLine()) != null) {
                if (line.toLowerCase().contains("true")) {
                    result = true;
                    break;
                }
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
        }
        return result;
    }

    @Deprecated
    public static boolean updatePlayerBalance(String authHash, String playerName, int balanceChange) {
        boolean result = false;
        try {
            // Construct data

            String data = URLEncoder.encode("playername", "UTF-8") + "=" + URLEncoder.encode(playerName, "UTF-8");
            data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");
            data += "&" + URLEncoder.encode("balancechange", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(balanceChange), "UTF-8");
            // Send data
            URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/updateplayerbalance.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(Settings.connectTimeout);
            conn.setReadTimeout(Settings.readTimeout);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = rd.readLine()) != null) {
                if (line.toLowerCase().contains("true")) {
                    result = true;
                    break;
                }
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    public static boolean updatePlayerBalance(String authHash, String playerName, int balanceChange, String logInfomation) {
        boolean result = false;
        try {
            // Construct data

            String data = URLEncoder.encode("playername", "UTF-8") + "=" + URLEncoder.encode(playerName, "UTF-8");
            data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");
            data += "&" + URLEncoder.encode("balancechange", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(balanceChange), "UTF-8");
            data += "&" + URLEncoder.encode("details", "UTF-8") + "=" + URLEncoder.encode(logInfomation, "UTF-8");
            // Send data
            URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/updateplayerbalance.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(Settings.connectTimeout);
            conn.setReadTimeout(Settings.readTimeout);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = rd.readLine()) != null) {
                if (line.toLowerCase().contains("true")) {
                    result = true;
                    break;
                }
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    public static HashMap<String, Integer> getTopTenPlayerVotersThisMonth() {
        HashMap<String, Integer> players = new HashMap<>();
        return players;
    }

    public static boolean logSignTransaction(String authHash, String userName, String signType, String serverName, String location, String signDetail, String Cost) {
        boolean result = false;
        try {
            // Construct data

            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8");
            data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");
            data += "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(signType, "UTF-8");
            data += "&" + URLEncoder.encode("server", "UTF-8") + "=" + URLEncoder.encode(serverName, "UTF-8");
            data += "&" + URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(location, "UTF-8");
            data += "&" + URLEncoder.encode("details", "UTF-8") + "=" + URLEncoder.encode(signDetail, "UTF-8");
            data += "&" + URLEncoder.encode("cost", "UTF-8") + "=" + URLEncoder.encode(Cost, "UTF-8");
            // Send data
            URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/logsigntransaction.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(Settings.connectTimeout);
            conn.setReadTimeout(Settings.readTimeout);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                if (line.toLowerCase().contains("true")) {
                    result = true;
                    break;
                }
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
        }
        return result;
    }

    public static boolean RemoveSign(String authHash, String location) {
        boolean result = false;
        try {
            // Construct data

            String data = URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(location, "UTF-8");
            data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");
            // Send data
            URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/removesign.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(Settings.connectTimeout);
            conn.setReadTimeout(Settings.readTimeout);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = rd.readLine()) != null) {
                if (line.toLowerCase().contains("true")) {
                    result = true;
                    break;
                }
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
        }
        return result;
    }

    public static boolean createSign(String authHash, String location, String line3, String name) {
        boolean result = false;
        try {
            // Construct data

            String data = URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(location, "UTF-8");
            data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");
            data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
            data += "&" + URLEncoder.encode("details", "UTF-8") + "=" + URLEncoder.encode(line3, "UTF-8");

            // Send data
            URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/createsign.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(Settings.connectTimeout);
            conn.setReadTimeout(Settings.readTimeout);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = rd.readLine()) != null) {
                if (line.toLowerCase().contains("true")) {
                    result = true;
                    break;
                }
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
        }
        return result;
    }

    public static String getSignDetails(String authHash, String location) {
        String output = "";
        try {
            // Construct data

            String data = URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(location, "UTF-8");
            data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");
            // Send data
            URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/getlinefromsign.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(Settings.connectTimeout);
            conn.setReadTimeout(Settings.readTimeout);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = rd.readLine()) != null) {
                output += line;
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
            return output;
        }

        String[] split = output.split(":");
        if (split.length == 2) {
            split[1] = split[1].replaceAll("}", "");
            split[1] = split[1].replaceAll("\"", "");
            output = split[1];

        }
        return output;
    }

    public static String[] getTempCommand(String authHash, String playerName) {
        try {
            // Construct data

            String data = URLEncoder.encode("playerName", "UTF-8") + "=" + URLEncoder.encode(playerName, "UTF-8");
            data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");
            // Send data
            URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/gettempcommand.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(Settings.connectTimeout);
            conn.setReadTimeout(Settings.readTimeout);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String input = rd.readLine();
            JSONArray json = (JSONArray) new JSONParser().parse(input);

            wr.close();
            rd.close();

            for (Object o : json) {
                String toString = o.toString();
                String[] split = toString.split("~");
                return split;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static boolean addTempCommand(String authHash, String playerName, String packageName) {
        boolean result = false;
        try {
            // Construct data

            String data = URLEncoder.encode("playerName", "UTF-8") + "=" + URLEncoder.encode(playerName, "UTF-8");
            data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");
            data += "&" + URLEncoder.encode("packageName", "UTF-8") + "=" + URLEncoder.encode(packageName, "UTF-8");
            // Send data
            URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/addtempcommand.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(Settings.connectTimeout);
            conn.setReadTimeout(Settings.readTimeout);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = rd.readLine()) != null) {
                if (line.toLowerCase().contains("true")) {
                    result = true;
                    break;
                }
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    public static boolean removeTempCommand(String authHash, String playerName, String packageName) {
        boolean result = false;
        try {
            // Construct data

            String data = URLEncoder.encode("playerName", "UTF-8") + "=" + URLEncoder.encode(playerName, "UTF-8");
            data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");
            data += "&" + URLEncoder.encode("packageName", "UTF-8") + "=" + URLEncoder.encode(packageName, "UTF-8");
            // Send data
            URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/removetempcommand.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(Settings.connectTimeout);
            conn.setReadTimeout(Settings.readTimeout);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = rd.readLine()) != null) {
                if (line.toLowerCase().contains("true")) {
                    result = true;
                    break;
                }
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    public static int getForumUserIDFromDiscordID(String authHash, String discordUserID) throws UnsupportedEncodingException, IOException, ParseException {
        int output = 0;
        try {
            authHash = Settings.checkAPIKey(authHash);
        } catch (APIKeyNotSetException ex) {
            Logger.getLogger(lolCon.class.getName()).log(Level.SEVERE, null, ex);
            return output;
        }

        String data = URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(authHash, "UTF-8");
        data += "&" + URLEncoder.encode("discordid", "UTF-8") + "=" + URLEncoder.encode(discordUserID + "", "UTF-8");

        // Send data
        URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/getforumuseridfromdiscordid.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(Settings.connectTimeout);
        conn.setReadTimeout(Settings.readTimeout);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String outputS = rd.readLine();
        JSONObject json = (JSONObject) new JSONParser().parse(outputS);

        wr.close();
        rd.close();

        return Integer.parseInt(json.get("forumid").toString());
    }

    public static String getDiscordUserIDFromForumID(String authHash, String userForumID) throws UnsupportedEncodingException, IOException, ParseException {
        String output = "";
        try {
            authHash = Settings.checkAPIKey(authHash);
        } catch (APIKeyNotSetException ex) {
            Logger.getLogger(lolCon.class.getName()).log(Level.SEVERE, null, ex);
            return output;
        }

        String data = URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(authHash, "UTF-8");
        data += "&" + URLEncoder.encode("forumid", "UTF-8") + "=" + URLEncoder.encode(userForumID + "", "UTF-8");

        // Send data
        URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/getdiscordidfromforumuserid.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(Settings.connectTimeout);
        conn.setReadTimeout(Settings.readTimeout);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String outputS = rd.readLine();
        JSONObject json = (JSONObject) new JSONParser().parse(outputS);

        wr.close();
        rd.close();

        return json.get("discordid").toString();
    }

    public static HashMap<Integer, Integer> getUpgrades(int playerFourmID, String authHash) {
        HashMap<Integer, Integer> upgrades = new HashMap<>();
        try {
            // Construct data

            String data = URLEncoder.encode("playerID", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(playerFourmID), "UTF-8");
            data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");
            // Send data
            URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/getplayerpackages.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(Settings.connectTimeout);
            conn.setReadTimeout(Settings.readTimeout);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String input = rd.readLine();
            JSONArray json = (JSONArray) new JSONParser().parse(input);
            wr.close();
            rd.close();

            for (Object o : json) {
                String toString = o.toString();
                if (toString.length() != 0) {
                    String[] split = toString.split("~");
                    for (String string : split) {
                        upgrades.put(Integer.parseInt(string.split(";")[0]), Integer.parseInt(string.split(";")[1]));
                    }
                }
            }
            return upgrades;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getUserUpgradeInfo(int user_upgrade_id, String authHash) throws UnsupportedEncodingException, IOException, ParseException, APIKeyNotSetException {
        String data = URLEncoder.encode("user_upgrade_id", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(user_upgrade_id), "UTF-8");
        data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");

        URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/getuserupgradeinfo.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(Settings.connectTimeout);
        conn.setReadTimeout(Settings.readTimeout);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONObject json = (JSONObject) new JSONParser().parse(rd.readLine());

        wr.close();
        rd.close();

        return (String) json.get("info");
    }

    public static boolean removeUserUpgrade(int user_upgrade_record_id, String authHash) throws UnsupportedEncodingException, IOException, ParseException, APIKeyNotSetException {
        boolean success = false;
        String data = URLEncoder.encode("user_upgrade_record_id", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(user_upgrade_record_id), "UTF-8");
        data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");

        URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/removeuserupgradeinfo.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(Settings.connectTimeout);
        conn.setReadTimeout(Settings.readTimeout);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONObject json = (JSONObject) new JSONParser().parse(rd.readLine());
        success = (boolean) json.get("successful");
        wr.close();
        rd.close();

        return success;
    }
    
    public static boolean setForumMinecraftLinkGroup(int forumID, String authHash) throws UnsupportedEncodingException, IOException, ParseException, APIKeyNotSetException {
        boolean success = false;
        String data = URLEncoder.encode("userforumid", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(forumID), "UTF-8");
        data += "&" + URLEncoder.encode("authhash", "UTF-8") + "=" + URLEncoder.encode(Settings.checkAPIKey(authHash), "UTF-8");

        URL url = new URL("https://www.lolnet.co.nz/api/v1.0/lolcoins/setplayerforumminecraftlink.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(Settings.connectTimeout);
        conn.setReadTimeout(Settings.readTimeout);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONObject json = (JSONObject) new JSONParser().parse(rd.readLine());
        success = (boolean) json.get("successful");
        wr.close();
        rd.close();

        return success;
    }
}
