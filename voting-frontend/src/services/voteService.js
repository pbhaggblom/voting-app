const API_URL = 'http://localhost:8080/vote'
const WS_URL = 'ws://localhost:8080/results'

export const postVote = (option) => {
  return fetch(`${API_URL}/${option}`, { method: 'POST' })
}

export const connectToResults = (onMessage) => {
  const socket = new WebSocket(WS_URL)
  socket.onmessage = (event) => onMessage(JSON.parse(event.data))
  return socket
}
