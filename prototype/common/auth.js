const USERS = {
    "provider1": {
        password: "password",
        type: "provider",
        name:"Provider1"
    },
    "maintenance1":{
        password: "password",
        type: "maintenance",
        name: "Maintenance1"
    }
};

function login(username, password) {
    const user = USERS[username];

    if (!user || user.password !== password){
        return false;
    }
    
    localStorage.setItem("userName", user.name);
    localStorage.setItem("userType", user.type);

    if (user.type === "provider") {
        window.location.href = "../provider/dash.html";
    } else if (user.type === "maintenance"){
        window.location.href = "../maintenance/dash.html";
    }

    return true;
}