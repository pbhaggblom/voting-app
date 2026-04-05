const API_URL = '/vote'

const protocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:'
const WS_URL = `${protocol}//${window.location.host}/results`

export const postVote = (option) => {
  return fetch(`${API_URL}/${option}`, { method: 'POST' })
}

export const getInitialResults = async () => {
  try {
    const response = await fetch(`${API_URL}/results`)
    const data = await response.json()
    return data
  } catch (error) {
    console.error('Could not fetch data:', error)
  }
}

export const connectToResults = (onMessage) => {
  const socket = new WebSocket(WS_URL)
  socket.onmessage = (event) => onMessage(JSON.parse(event.data))
  return socket
}
