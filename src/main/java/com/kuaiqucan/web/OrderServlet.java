package com.kuaiqucan.web;

import com.kuaiqucan.entity.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> list = getAllOrder(req);
        Order newOne = new Order();
        newOne.setOid("" + list.size());
        setAllPropsOfPut(newOne, req);
        list.add(newOne);
        this.getServletContext().setAttribute("order", list);
        resp.getWriter().println("{status:'success'}");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object oid = getParamsOfStream(req).get("oid");
        if (oid == null) {
            resp.getWriter().println("{status:'error',message:'请输入订单id号'}");
            return;
        }
        List<Order> list = getAllOrder(req);
        boolean flag = false;
        int i;
        for (i = 0 ; i < list.size() ; i++) {
            if (isoid(list.get(i), oid)) {
                flag = true;
                break;
            }
        }
        if (flag) {
            list.remove(i);
            resp.getWriter().println("{status:'success'}");
        } else {
            resp.getWriter().println("{status:'error',message:'未找到该订单'}");
        }
    }

    private Map<String, String> getParamsOfStream(HttpServletRequest req) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(req.getInputStream()));
        Map<String, String> props = new HashMap<>();
        String line;
        while ((line = in.readLine()) != null) {
            char t[] = line.toCharArray();
            String key = "";
            String value = "";
            boolean flag = true;
            for (int i = 0; i < t.length; i++) {
                if (t[i] == '=') {
                    flag = false;
                    continue;
                }
                if (t[i] == ';') {
                    flag = true;
                    props.put(key, value);
                    key = "";
                    value = "";
                    continue;
                }
                if (flag) {
                    key += "" + t[i];
                } else {
                    value += "" + t[i];
                }
            }
        }
        return props;
    }

    private void setAllPropsOfPut(Order target, HttpServletRequest req) throws IOException {
        Map<String, String> props = getParamsOfStream(req);
        String oname = props.get("oname");
        String otype = props.get("otype");
        String obrand = props.get("obrand");
        String ocount = props.get("ocount");
        String oprice = props.get("oprice");
        if (oname != null) {
            target.setOname(oname);
        }
        if (otype != null) {
            target.setOtype(otype);
        }
        if (obrand != null) {
            target.setObrand(obrand);
        }
        if (ocount != null) {
            target.setOcount(ocount);
        }
        if (oprice != null) {
            target.setOprice(oprice);
        }
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Object oid = req.getParameter("oid");
//        if (oid == null) {
//            resp.getWriter().println("{status:'error',message:'请输入订单id号'}");
//            return;
//        }
//        List<Order> list = getAllOrder(req);
//        boolean flag = false;
//        for (Order t : list) {
//            if (isoid(t, oid)) {
//                setAllPropsOfPost(t, req);
//                flag = true;
//            }
//        }
//        if (flag) {
//            resp.getWriter().println("{status:'success'}");
//        } else {
//            resp.getWriter().println("{status:'error',message:'未找到该订单'}");
//        }
//    }

    private void setAllPropsOfPost(Order t, HttpServletRequest req) {
        Object oname = req.getParameter("oname");
        Object otype = req.getParameter("otype");
        Object obrand = req.getParameter("obrand");
        Object ocount = req.getParameter("ocount");
        Object oprice = req.getParameter("oprice");
        if (oname != null) {
            if (!("" + oname).equals("")) {
                t.setOname("" + oname);
            }
        }
        if (otype != null) {
            if (!("" + otype).equals("")) {
                t.setOtype("" + otype);
            }
        }
        if (obrand != null) {
            if (!("" + obrand).equals("")) {
                t.setObrand("" + obrand);
            }
        }
        if (ocount != null) {
            if (!("" + ocount).equals("")) {
                t.setOcount("" + ocount);
            }
        }
        if (oprice != null) {
            if (!("" + oprice).equals("")) {
                t.setOprice("" + oprice);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> list = getAllOrder(req);
        List<Order> exhibition = new ArrayList<>();
        List<Object> props = getAllPropsOfGet(req);
        if (list != null) {
            for (Order t : list) {
                if (isHasAllSame(t, props)) {
                    exhibition.add(t);
                }
            }
        }
        resp.getWriter().println("{" + toJsonOfOrder(exhibition) + "}");
    }

    private boolean isHasAllSame(Order target, List<Object> props) {
        if (props.get(0) != null) {
            if (props.get(0) != target.getOid()) {
                return false;
            }
        }
        if (props.get(1) != null) {
            if (props.get(1) != target.getOname()) {
                return false;
            }
        }
        if (props.get(2) != null) {
            if (props.get(2) != target.getOtype()) {
                return false;
            }
        }
        if (props.get(3) != null) {
            if (props.get(3) != target.getObrand()) {
                return false;
            }
        }
        if (props.get(4) != null) {
            if (props.get(4) != target.getOcount()) {
                return false;
            }
        }
        if (props.get(5) != null) {
            if (props.get(5) != target.getOprice()) {
                return false;
            }
        }
        return true;
    }

    private boolean isoid(Order target, Object oid) {
        if (oid != null) {
            if (oid.equals(target.getOid())) {
                return true;
            }
        }
        return false;
    }

    private String toJsonOfOrder(List<Order> exhibition) {
        String p = "data:[";
        for (int i = 0; i < exhibition.size(); i++) {
            Order t = exhibition.get(i);
            p += "{oid:'" + t.getOid() + "',";
            p += "oname:'" + t.getOname() + "',";
            p += "otype:'" + t.getOtype() + "',";
            p += "obrand:'" + t.getObrand() + "',";
            p += "ocount:'" + t.getOcount() + "',";
            p += "oprice:'" + t.getOprice() + "'},";
        }
        p += "]";
        return p;
    }

    private List<Object> getAllPropsOfGet(HttpServletRequest req) {
        List<Object> props = new ArrayList<>();
        props.add(req.getAttribute("oid"));
        props.add(req.getAttribute("oname"));
        props.add(req.getAttribute("otype"));
        props.add(req.getAttribute("obrand"));
        props.add(req.getAttribute("ocount"));
        props.add(req.getAttribute("oprice"));
        return props;
    }

    private List<Order> getAllOrder(HttpServletRequest req) {
        List<Order> t = (List<Order>) this.getServletContext().getAttribute("order");
        if (t == null) {
            t = new ArrayList<Order>();
            this.getServletContext().setAttribute("order", t);
        }
        return t;
    }
}
