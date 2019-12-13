const fs = require("fs");
const axios = require("axios");

const JSON_FILE = "./public/apods.json";

axios
  .get(
    "https://api.nasa.gov/planetary/apod?api_key=PEw2feZBRngLi4JWjEDyBfFOcpGyb5clPt0zoZHb"
  )
  .then(res => {
    fs.readFile(JSON_FILE, (err, data) => {
      if (err) return console.error("unable to write to apods.json");
      let json = JSON.parse(data);

      json.push(res.data);
      fs.writeFileSync(JSON_FILE, JSON.stringify(json));
    });
  });