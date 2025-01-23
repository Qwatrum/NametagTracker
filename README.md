# Nametagtracker

---
###### A minecraft paper plugin adding an easy way to track your named entities

---

## [Demo video](https://cloud-n5z61j729-hack-club-bot.vercel.app/0ntt.mp4)

## Get started:
- Download the plugin [here](https://modrinth.com/plugin/nametagtracker) on Modrinth
- Or clone the repo and compile it on your own (Maven -> `package`)

## Usage:
- Navigate to your `plugins` folder in your paper minecraft server
- Upload `Nametagtracker.jar` and reload your server
- A config storing the data, will be automatically generated in `Nametagtracker` - `config.yml`

## Features:
- Name an entity with a name tag
- It will be added to your overview page
- You can open it by using this command: `/overview`
- Hover over the names to see the entity type, position (with dimension), health and UUID
- You can click and confirm a name to remove it
- `/about` shows some information about this plugin

## Saved data structure:
- When an entity is named or removed, the config will save the new data
- The structure looks like this:
- ```
  players:
  (Player 1 UUID):
    entities:
      (Entity 1 UUID): Bob
      (Entity 2 UUID): James
  (Player 2 UUID):
    entities:
      (Entity 3 UUID): Mike
      (Entity 4 UUID): John

  ```

## Possibles uses:
- When using farms, named entities are often important
- But it can always happen that they die or are killed
- With this plugin you can see from anywhere whether they live (as long as the chunk is loaded)
- You can see where the entities are (if someone kidnapped your pet).
- Or even as a waypoint

## Screenshots:
![image0](https://cloud-d3mbsrjee-hack-club-bot.vercel.app/2ntt__1_.png)
![image6](https://cloud-h1648500a-hack-club-bot.vercel.app/0ntt__2_.png)
![image1](https://cloud-d3mbsrjee-hack-club-bot.vercel.app/1ntt__3_.png)
![image4](https://cloud-h1648500a-hack-club-bot.vercel.app/2ntt__4_.png)
![image5](https://cloud-h1648500a-hack-club-bot.vercel.app/1ntt__5_.png)
![image2](https://cloud-3v9by2tbc-hack-club-bot.vercel.app/0ntt_image.png)

## Credits:
- Code: Qwatrum

made with <3 by Qwatrum