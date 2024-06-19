/**
 * convert porvider json to readable table format
 */


function processJson(jsonString1) {
    $("#ProviderResponsepopups").html(buildTable(jsonString1));
  
}


function buildTable(a) {
    var e = document.createElement("table"), d, b;
    if (isArray(a))
        return buildArray(a);
    for (var c in a)
        "object" != typeof a[c] || isArray(a[c]) ? "object" == typeof a[c] && isArray(a[c]) ? (d = e.insertRow(-1),
        b = d.insertCell(-1),
        b.colSpan = 2,
        b.innerHTML = '<div class="accordion--quick-controls"><div class="accordion__section"><div class="accordion__header"><a href="#"><i class="material-icons">add</i>'+encodeText(c)+'</a></div><div class="accordion__content"><table style="width:100%">' + $(buildArray(a[c]), !1).html() + "</table></div></div></div>") : (d = e.insertRow(-1),
        b = d.insertCell(-1),
        b.innerHTML = "<div class='j2t_td_head'>" + encodeText(c) + "</div>",
        d = d.insertCell(-1),
        d.innerHTML = "<div class='j2t_td_row_even'>" + encodeText(a[c]) + "</div>") : (d = e.insertRow(-1),
        b = d.insertCell(-1),
        b.colSpan = 2,
        b.innerHTML = '<div class="accordion--quick-controls"><div class="accordion__section"><div class="accordion__header"><a href="#"><i class="material-icons">add</i>' + encodeText(c) + '</a></div><div class="accordion__content"><table style="width:100%">' + $(buildTable(a[c]), !1).html() + "</table></div></div></div>");
    return e
}

function buildArray(a) {
    var e = document.createElement("table"), d, b, c = !1, p = !1, m = {}, h = -1, n = 0, l;
    l = "";
    if (0 == a.length)
        return "<div></div>";
    d = e.insertRow(-1);
    for (var f = 0; f < a.length; f++)
        if ("object" != typeof a[f] || isArray(a[f]))
            "object" == typeof a[f] && isArray(a[f]) ? (b = d.insertCell(h),
            b.colSpan = 2,
            b.innerHTML = '<div class="j2t_td_head"></div><table style="width:100%">' + $(buildArray(a[f]), !1).html() + "</table>",
            c = !0) : p || (h += 1,
            p = !0,
            b = d.insertCell(h),
            m.empty = h,
            b.innerHTML = "<div class='j2t_td_head'>&nbsp;</div>");
        else
            for (var k in a[f])
                l = "-" + k,
                l in m || (c = !0,
                h += 1,
                b = d.insertCell(h),
                m[l] = h,
                b.innerHTML = "<div class='j2t_td_head'>" + encodeText(k) + "</div>");
    c || e.deleteRow(0);
    n = h + 1;
    for (f = 0; f < a.length; f++)
        if (d = e.insertRow(-1),
        td_class = isEven(f) ? "j2d_td_row_even" : "j2d_td_row_odd",
        "object" != typeof a[f] || isArray(a[f]))
            if ("object" == typeof a[f] && isArray(a[f]))
                for (h = m.empty,
                c = 0; c < n; c++)
                    b = d.insertCell(c),
                    b.className = td_class,
                    l = c == h ? '<table style="width:100%">' + $(buildArray(a[f]), !1).html() + "</table>" : " ",
                    b.innerHTML = "<div class='" + td_class + "'>" + encodeText(l) + "</div>";
            else
                for (h = m.empty,
                c = 0; c < n; c++)
                    b = d.insertCell(c),
                    l = c == h ? a[f] : " ",
                    b.className = td_class,
                    b.innerHTML = "<div class='" + td_class + "'>" + encodeText(l) + "</div>";
        else {
            for (c = 0; c < n; c++)
                b = d.insertCell(c),
                b.className = td_class,
                b.innerHTML = "<div class='" + td_class + "'>&nbsp;</div>";
            for (k in a[f])
                c = a[f],
                l = "-" + k,
                h = m[l],
                b = d.cells[h],
                b.className = td_class,
                "object" != typeof c[k] || isArray(c[k]) ? "object" == typeof c[k] && isArray(c[k]) ? b.innerHTML = '<div class="accordion--quick-controls"><div class="accordion__section"><div class="accordion__header"><a href="#"><i class="material-icons">add</i></a></div><div class="accordion__content"><table style="width:100%">' + $(buildArray(c[k]), !1).html() + "</table></div></div></div>" : b.innerHTML = "<div class='" + td_class + "'>" + encodeText(c[k]) + "</div>" : b.innerHTML = '<table style="width:100%">' + $(buildTable(c[k]), !1).html() + "</table>"
        }
    return e
}
function encodeText(a) {
    return $("<div />").text(a).html()
}
function isArray(a) {
    return "[object Array]" === Object.prototype.toString.call(a)
}
function isEven(a) {
    return 0 == a % 2
}

function log(a, e, d) {
    console.log(a)
};
function getJsonVar() {
    var a={"profileName":"UK Profile","profileState":{"value":"Effective"},"country":"UK","profileVersion":1,"resultCodes":[{"country":null,"address":{"value":"Nomatch"},"pass":{"value":"Nomatch"},"match":null,"description":"UK Credit Header Database.  Provides authentication of name, address and date of birth against Credit Header information for an Anti Money Laundering Check.","forename":{"value":"Nomatch"},"alert":{"value":"Nomatch"},"surname":{"value":"Nomatch"},"dob":{"value":"Nomatch"},"mismatch":null,"name":"UK Credit Header (AML)","warning":null,"comment":[{"code":101,"description":"No middle initial specified by user.","override":null},{"code":111,"description":"No/insufficient details supplied by user for address #1","override":null},{"code":151,"description":"First year of residence for address #1 not supplied by user.","override":null},{"code":161,"description":"Last year of residence for address #1 not supplied by user. Applying default.","override":null},{"code":500,"description":"Check against the Credit Database not carried out due to insufficient valid information supplied by the user.","override":null}],"id":155},{"country":null,"address":{"value":"Nomatch"},"pass":{"value":"Nomatch"},"match":null,"description":"UK National Identity Register check. Performs authentication of first, last name, date of birth, address and phone numbers against UK National Identity Register.","forename":{"value":"Nomatch"},"alert":{"value":"Nomatch"},"surname":{"value":"Nomatch"},"dob":{"value":"Nomatch"},"mismatch":null,"name":"UK National Identity Register","warning":null,"comment":[{"code":910,"description":"No/insufficient address supplied.  These are mandatory fields and so no authentication was carried out for this item check.","override":null},{"code":500,"description":"Check against the source database not carried out due to insufficient valid information supplied by the user","override":null}],"id":245},{"country":null,"address":{"value":"Nomatch"},"pass":{"value":"Nomatch"},"match":null,"description":"UK Landline Telephone Database.  Provides authentication against first initial, surname, address and appends land telephone number or ex-directory status of bill payer.","forename":{"value":"Nomatch"},"alert":{"value":"Nomatch"},"surname":{"value":"Nomatch"},"dob":{"value":"NA"},"mismatch":null,"name":"UK Landline (Append)","warning":null,"comment":[{"code":101,"description":"No/insufficient details supplied for address #1. No search performed.","override":null},{"code":102,"description":"No/insufficient details supplied for address #2. No search performed.","override":null},{"code":103,"description":"No/insufficient details supplied for address #3. No search performed.","override":null},{"code":104,"description":"No/insufficient details supplied for address #4. No search performed.","override":null}],"id":106,"publishedTelephoneNumber":null},{"country":null,"address":{"value":"NA"},"pass":{"value":"Mismatch"},"match":null,"description":"UK Births Registry Database.  Provides authentication against the first, last name, date of birth registration and mothers maiden name for individuals born in England and Wales between 1984 and 2003 inclusive.","forename":{"value":"Nomatch"},"alert":{"value":"Nomatch"},"surname":{"value":"Nomatch"},"dob":{"value":"Nomatch"},"mismatch":[{"code":7001,"description":"No match was found on forename, surname and date of birth","override":null}],"name":"UK Births","warning":null,"comment":[{"code":103,"description":"No middle initial was supplied by the user","override":null},{"code":107,"description":"No mother's maiden name was supplied by the user","override":null},{"code":109,"description":"No country of registration was supplied by the user, assuming England/Wales","override":null}],"id":133},{"country":null,"address":{"value":"Nomatch"},"pass":{"value":"NA"},"match":null,"description":"UK NCOA (Alert Full) Database check.  Performs authentication of first, last name and address details (plus provides fraud flags, active, expired and pending redirect information) against national change of address data records.","forename":{"value":"Nomatch"},"alert":{"value":"Nomatch"},"surname":{"value":"Nomatch"},"dob":{"value":"NA"},"mismatch":null,"name":"UK NCOA (Alert Full)","warning":null,"comment":[{"code":103,"description":"Insufficient address details were supplied for address #1, unable to perform check","override":null},{"code":104,"description":"Insufficient address details were supplied for address #2, unable to perform check","override":null},{"code":105,"description":"Insufficient address details were supplied for address #3, unable to perform check","override":null},{"code":106,"description":"Insufficient address details were supplied for address #4, unable to perform check","override":null}],"id":239},{"country":null,"address":{"value":"Nomatch"},"pass":{"value":"NA"},"match":null,"description":"UK Deceased Persons Database.  Provides checking of a first and last name at an address against the registered deceased persons database.","forename":{"value":"Nomatch"},"alert":{"value":"Nomatch"},"surname":{"value":"Nomatch"},"dob":{"value":"NA"},"mismatch":null,"name":"UK Mortality","warning":null,"comment":[{"code":101,"description":"No middle initial specified by user","override":null},{"code":111,"description":"No details supplied by user for address 1","override":null},{"code":112,"description":"No details supplied by user for address 2","override":null},{"code":113,"description":"No details supplied by user for address 3","override":null},{"code":114,"description":"No details supplied by user for address 4","override":null}],"id":110}],"authenticationID":"fc967b39-9c77-4c24-ad4b-fc23eb3d6bc5","userBreakpoint":null,"noRetry":null,"bandText":"No Match","score":0,"customerRef":"002-C-0000000008","profileRevision":4,"chainID":null,"profileID":"49693f46-adf1-4d23-99ed-b0c9ac9a41cb","timestamp":1494345527656};
	return a;
}