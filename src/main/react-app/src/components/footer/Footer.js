import moment from 'moment'

const Footer = () => {
  return (
    <Footer>
      <p>Copyright &copy; Sell My Picture {moment(new Date()).format('YYYY')}</p>
    </Footer>
  )
}

export default Footer