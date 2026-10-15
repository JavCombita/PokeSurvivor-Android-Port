/* Offline-only compatibility: no network transports are available in this port. */
G.Social = {
  room: null, friends: [], started: false, MAX_PLAYERS: 1, uid: null,
  me: { name: 'JavCombita' },
  on() {}, start: async () => ({ ok: false }), setOpen() {},
  setPlaying() {}, publishProfile() {}, profileData: () => ({ name: 'JavCombita', stats: {} }),
  getProfile: async () => null
};
G.Net = {};
G.Coop = {};
G.Ranking = {};
