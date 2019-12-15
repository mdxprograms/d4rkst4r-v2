const fs = require("fs");
const axios = require("axios");

const JSON_FILE = "./public/apods.json";

axios
  .get(
    "https://api.nasa.gov/planetary/apod?api_key=PEw2feZBRngLi4JWjEDyBfFOcpGyb5clPt0zoZHb"
  )
  .then(res => {
    if (res.status !== 200) {
      return console.log("Failed to fetch data. Status: ", 200);
    }

    fs.readFile(JSON_FILE, (err, data) => {
      if (err) return console.error("unable to write to apods.json");
      let json = JSON.parse(data);

      json.push(res.data);
      fs.writeFileSync(JSON_FILE, JSON.stringify(json.sort(() => Math.random() - 0.5)));
    });

    return console.log("Fetched APOD data successfully");
  });
