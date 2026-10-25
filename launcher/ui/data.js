/* Placeholder content. Swap these arrays when real sources exist:
 * NEWS for a changelog, DISCORD for a feed, MEDIA for real screenshots
 * (any data: URI or file path works as `src`). */

(function () {
  const svg = (body) =>
    "data:image/svg+xml," +
    encodeURIComponent(
      '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 480 320">' +
        body +
        "</svg>"
    );

  const VIGNETTE =
    '<radialGradient id="vig" cx=".5" cy=".45" r=".75">' +
    '<stop offset=".55" stop-color="#000" stop-opacity="0"/>' +
    '<stop offset="1" stop-color="#000" stop-opacity=".5"/>' +
    "</radialGradient>";

  const scene = (defs, body) =>
    svg("<defs>" + defs + VIGNETTE + "</defs>" + body + '<rect width="480" height="320" fill="url(#vig)"/>');

  const fields = scene(
    '<linearGradient id="sky" x1="0" y1="0" x2="0" y2="1">' +
      '<stop offset="0" stop-color="#5f7f93"/><stop offset=".55" stop-color="#a7b09a"/>' +
      '<stop offset="1" stop-color="#d8d2ac"/></linearGradient>',
    '<rect width="480" height="320" fill="url(#sky)"/>' +
      '<circle cx="366" cy="74" r="52" fill="#f7e8bc" opacity=".16"/>' +
      '<circle cx="366" cy="74" r="25" fill="#f7e8bc" opacity=".8"/>' +
      '<path d="M0 178 96 138 190 176 286 132 384 174 480 142V320H0Z" fill="#6d7f5a"/>' +
      '<path d="M0 218 122 186 250 222 372 184 480 214V320H0Z" fill="#566b44"/>' +
      '<path d="M0 258 150 232 296 266 424 234 480 250V320H0Z" fill="#41552f"/>' +
      '<g stroke="#7d9a5c" stroke-width="2" opacity=".65"><path d="M54 306l3-16M64 308l2-11M78 304l4-14M92 310l2-12"/>' +
      '<path d="M300 300l3-14M312 304l2-10M324 302l4-15M338 306l2-9"/></g>' +
      '<g fill="#33452a" opacity=".45"><ellipse cx="120" cy="288" rx="26" ry="6"/>' +
      '<ellipse cx="360" cy="296" rx="34" ry="7"/></g>'
  );

  const coast = scene(
    '<linearGradient id="sky2" x1="0" y1="0" x2="0" y2="1">' +
      '<stop offset="0" stop-color="#54748c"/><stop offset=".7" stop-color="#b9c3b0"/></linearGradient>' +
      '<linearGradient id="sea" x1="0" y1="0" x2="0" y2="1">' +
      '<stop offset="0" stop-color="#3f6d76"/><stop offset="1" stop-color="#274c57"/></linearGradient>',
    '<rect width="480" height="320" fill="url(#sky2)"/>' +
      '<circle cx="110" cy="66" r="22" fill="#f6e7bb" opacity=".75"/>' +
      '<rect y="176" width="480" height="144" fill="url(#sea)"/>' +
      '<path d="M0 190 70 150 138 186 196 160 250 190Z" fill="#5c6552"/>' +
      '<path d="M0 190 70 150 138 186 196 160 250 190V320H0Z" fill="#3f4a3c"/>' +
      '<path d="M296 320 328 220 392 190 448 224 480 214V320Z" fill="#4a5343"/>' +
      '<g stroke="#9dc3bd" stroke-width="2" opacity=".5"><path d="M40 236h56M130 258h74M300 240h60M360 268h80M70 288h90"/></g>' +
      '<path d="M170 320 260 230 330 250 300 320Z" fill="#cbbd92" opacity=".85"/>' +
      '<path d="M250 320 316 250 360 262 340 320Z" fill="#e2d5ab" opacity=".7"/>'
  );

  const wall = scene(
    '<linearGradient id="bg3" x1="0" y1="0" x2="0" y2="1">' +
      '<stop offset="0" stop-color="#28331f"/><stop offset="1" stop-color="#151d13"/></linearGradient>' +
      '<radialGradient id="torch"><stop offset="0" stop-color="#ffcf8a" stop-opacity=".85"/>' +
      '<stop offset="1" stop-color="#ffcf8a" stop-opacity="0"/></radialGradient>',
    '<rect width="480" height="320" fill="url(#bg3)"/>' +
      '<rect x="46" y="56" width="388" height="264" fill="#5c584c"/>' +
      '<g fill="#6b6759">' +
      '<rect x="46" y="56" width="96" height="46"/><rect x="150" y="56" width="96" height="46"/>' +
      '<rect x="254" y="56" width="96" height="46"/><rect x="358" y="56" width="76" height="46"/>' +
      '<rect x="46" y="110" width="76" height="46"/><rect x="130" y="110" width="96" height="46"/>' +
      '<rect x="234" y="110" width="96" height="46"/><rect x="338" y="110" width="96" height="46"/>' +
      '<rect x="46" y="164" width="96" height="46"/><rect x="150" y="164" width="96" height="46"/>' +
      '<rect x="254" y="164" width="96" height="46"/><rect x="358" y="164" width="76" height="46"/></g>' +
      '<g opacity=".55"><rect x="52" y="62" width="84" height="34" fill="#565244"/>' +
      '<rect x="156" y="116" width="84" height="34" fill="#565244"/>' +
      '<rect x="260" y="170" width="84" height="34" fill="#565244"/></g>' +
      '<g fill="#4a6a34" opacity=".75"><path d="M60 210c14-10 26-6 32 4-12 4-22 4-32-4Z"/>' +
      '<path d="M368 96c12-8 22-4 28 5-10 3-19 3-28-5Z"/></g>' +
      '<circle cx="430" cy="150" r="72" fill="url(#torch)"/>' +
      '<rect x="424" y="170" width="7" height="46" fill="#3a2c1c"/>' +
      '<path d="M427 152c9 8 9 16 0 22-9-6-9-14 0-22Z" fill="#f6b25e"/>'
  );

  const forest = scene(
    '<linearGradient id="mist" x1="0" y1="0" x2="0" y2="1">' +
      '<stop offset="0" stop-color="#7c8b76"/><stop offset=".55" stop-color="#566a4c"/>' +
      '<stop offset="1" stop-color="#2b3a28"/></linearGradient>',
    '<rect width="480" height="320" fill="url(#mist)"/>' +
      '<g fill="#44583c" opacity=".8"><path d="M60 220 104 96 148 220Z"/><path d="M110 232 148 124 186 232Z"/>' +
      '<path d="M330 236 372 110 414 236Z"/><path d="M382 226 418 126 454 226Z"/></g>' +
      '<g fill="#33452d"><path d="M-10 250 40 118 90 250Z"/><path d="M84 258 130 140 176 258Z"/>' +
      '<path d="M292 256 338 132 384 256Z"/><path d="M390 260 432 150 474 260Z"/></g>' +
      '<g fill="#241f18"><rect x="36" y="238" width="10" height="52"/><rect x="126" y="246" width="9" height="44"/>' +
      '<rect x="334" y="244" width="10" height="46"/><rect x="428" y="248" width="9" height="42"/></g>' +
      '<rect y="266" width="480" height="26" fill="#c9c8ad" opacity=".16"/>' +
      '<rect y="292" width="480" height="28" fill="#1f2a1c"/>'
  );

  const dungeon = scene(
    '<linearGradient id="floor" x1="0" y1="0" x2="0" y2="1">' +
      '<stop offset="0" stop-color="#2c2a25"/><stop offset="1" stop-color="#131210"/></linearGradient>' +
      '<radialGradient id="ember"><stop offset="0" stop-color="#ffab55" stop-opacity=".9"/>' +
      '<stop offset="1" stop-color="#ffab55" stop-opacity="0"/></radialGradient>',
    '<rect width="480" height="320" fill="url(#floor)"/>' +
      '<path d="M0 220 240 150 480 220V320H0Z" fill="#211f1a"/>' +
      '<g fill="#3a352c"><rect x="64" y="150" width="34" height="130"/><rect x="382" y="150" width="34" height="130"/></g>' +
      '<g fill="#4a4438"><rect x="56" y="138" width="50" height="16"/><rect x="374" y="138" width="50" height="16"/></g>' +
      '<path d="M150 220 240 186 330 220V250H150Z" fill="#0e0d0b"/>' +
      '<g stroke="#3c372e" stroke-width="2" opacity=".7"><path d="M0 260h480M0 292h480M120 220v100M240 186v134M360 220v100"/></g>' +
      '<circle cx="82" cy="132" r="54" fill="url(#ember)" opacity=".8"/>' +
      '<circle cx="398" cy="132" r="54" fill="url(#ember)" opacity=".8"/>' +
      '<path d="M81 116c7 8 7 14 0 19-7-5-7-11 0-19Z" fill="#ffc07a"/>' +
      '<path d="M397 116c7 8 7 14 0 19-7-5-7-11 0-19Z" fill="#ffc07a"/>'
  );

  const night = scene(
    '<linearGradient id="nite" x1="0" y1="0" x2="0" y2="1">' +
      '<stop offset="0" stop-color="#0e1626"/><stop offset=".6" stop-color="#243347"/>' +
      '<stop offset="1" stop-color="#4b5a63"/></linearGradient>',
    '<rect width="480" height="320" fill="url(#nite)"/>' +
      '<g fill="#e8e2c8">' +
      '<circle cx="42" cy="48" r="1.6"/><circle cx="96" cy="82" r="1.2"/><circle cx="150" cy="40" r="1.8"/>' +
      '<circle cx="210" cy="68" r="1.1"/><circle cx="268" cy="38" r="1.5"/><circle cx="330" cy="74" r="1.3"/>' +
      '<circle cx="388" cy="46" r="1.7"/><circle cx="438" cy="92" r="1.2"/><circle cx="72" cy="120" r="1"/>' +
      '<circle cx="360" cy="126" r="1.1"/></g>' +
      '<circle cx="364" cy="84" r="26" fill="#f2e7c2"/>' +
      '<circle cx="374" cy="76" r="24" fill="#1b2739"/>' +
      '<path d="M0 222 84 188 164 220 250 182 336 218 420 190 480 216V320H0Z" fill="#1a2430"/>' +
      '<path d="M0 258 110 232 220 262 340 230 480 258V320H0Z" fill="#131b24"/>' +
      '<rect y="286" width="480" height="34" fill="#20323d" opacity=".8"/>' +
      '<g stroke="#8fa2a6" stroke-width="1.5" opacity=".35"><path d="M338 296h44M318 304h76M330 312h52"/></g>'
  );

  window.NEWS = [
    {
      date: "24 Oct 2026",
      tag: "Launcher",
      title: "Launcher preview",
      body: "The 2010-style shell is here: update log, media tab, Discord placeholder and a settings pane that starts the fat client jar with your Java, memory and 592HD mode.",
    },
    {
      date: "21 Oct 2026",
      tag: "592HD",
      title: "Ground art and per-pixel light",
      body: "World textures and material maps share the ground atlas, and flat tiles keep a usable normal so grass draws its own art instead of dropping to a hole.",
    },
    {
      date: "19 Oct 2026",
      tag: "Client",
      title: "Resize redraw",
      body: "Dragging the window across monitors asks the canvas for a repaint instead of leaving a black frame until the next tick.",
    },
    {
      date: "16 Oct 2026",
      tag: "592HD",
      title: "Sky, fog and water",
      body: "The HD pipeline follows the client's sky colours, fades distance into fog and gives still water its reflection pass.",
    },
    {
      date: "14 Oct 2026",
      tag: "Settings",
      title: "One properties file",
      body: "VM size, 592HD mode, server address and RSA keys live in launcher.properties. The GUI edits it, and the client reads the same file on startup.",
    },
    {
      date: "12 Oct 2026",
      tag: "Client",
      title: "Camera choices persist",
      body: "Zoom and fog distance are remembered across restarts and clamped to the 165% ceiling.",
    },
  ];

  window.DISCORD = {
    channel: "#open592",
    online: 12,
    messages: [
      { initials: "AV", avatar: "a", name: "Avalon", time: "12:04", text: "high preset with water on the new build looks unreal" },
      { initials: "RK", avatar: "b", name: "Rook", time: "12:07", text: "anyone else get grass drawing as glass before the atlas fix?" },
      { initials: "MS", avatar: "c", name: "Moss", time: "12:11", text: "launcher when" },
      { initials: "JN", avatar: "d", name: "Juno", time: "12:12", text: "right now apparently. check the media tab" },
    ],
  };

  window.MEDIA = [
    { title: "Lumbridge fields", caption: "grass, sky and distant hills", src: fields },
    { title: "Coastline", caption: "water, cliffs and a low tide", src: coast },
    { title: "Varrock wall", caption: "stone, moss and torchlight", src: wall },
    { title: "Forest edge", caption: "layered canopy in morning mist", src: forest },
    { title: "Dungeon corridor", caption: "torchlight on wet stone", src: dungeon },
    { title: "Night sky", caption: "moonrise over the hills", src: night },
  ];
})();
